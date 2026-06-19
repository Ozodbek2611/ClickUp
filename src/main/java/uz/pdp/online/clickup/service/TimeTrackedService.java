package uz.pdp.online.clickup.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.online.clickup.entity.domain.Task;
import uz.pdp.online.clickup.entity.domain.TimeTracked;
import uz.pdp.online.clickup.entity.enums.TaskHistoryType;
import uz.pdp.online.clickup.exceptions.NotFoundException;
import uz.pdp.online.clickup.mapper.TimeTrackedMapper;
import uz.pdp.online.clickup.model.timeTrackedDto.TimeTrackedRequestDto;
import uz.pdp.online.clickup.model.timeTrackedDto.TimeTrackedResponseDto;
import uz.pdp.online.clickup.repository.TaskRepository;
import uz.pdp.online.clickup.repository.TimeTrackedRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TimeTrackedService {

    private final TimeTrackedRepository timeTrackedRepository;
    private final TaskRepository taskRepository;
    private final TaskHistoryService taskHistoryService;
    private final TimeTrackedMapper timeTrackedMapper;

    @Transactional
    public TimeTrackedResponseDto startTrack(TimeTrackedRequestDto dto) {
        Task task = taskRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new NotFoundException("Task not found with ID: " + dto.getTaskId()));

        TimeTracked timeTracked = TimeTracked.builder()
                .task(task)
                .startedAt(new Timestamp(System.currentTimeMillis()))
                .build();

        TimeTracked saved = timeTrackedRepository.save(timeTracked);
        taskHistoryService.logChange(task, "time_track", null, "Started at: " + saved.getStartedAt(), TaskHistoryType.ADD);
        log.info("Time tracking successfully started with record ID: {} for task ID: {}", saved.getId(), dto.getTaskId());
        return timeTrackedMapper.toResponseDto(saved);
    }

    @Transactional
    public TimeTrackedResponseDto stopTrack(UUID id) {
        TimeTracked timeTracked = timeTrackedRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Time track record not found with ID: " + id));

        timeTracked.setStoppedAt(new Timestamp(System.currentTimeMillis()));
        TimeTracked updated = timeTrackedRepository.save(timeTracked);

        taskHistoryService.logChange(updated.getTask(), "time_track", "Started: " + updated.getStartedAt(),
                "Stopped: " + updated.getStoppedAt(), TaskHistoryType.UPDATE);
        log.info("Time tracking successfully stopped for record ID: {}", id);
        return timeTrackedMapper.toResponseDto(timeTracked);
    }

    @Transactional(readOnly = true)
    public List<TimeTrackedResponseDto> getByTaskId(UUID taskId) {
        List<TimeTracked> history = timeTrackedRepository.findAllByTaskId(taskId);
        return timeTrackedMapper.toResponseDtoList(history);
    }
}
