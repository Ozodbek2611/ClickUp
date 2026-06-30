package uz.pdp.online.clickup.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.online.clickup.audit.service.TaskHistoryService;
import uz.pdp.online.clickup.task.entity.Task;
import uz.pdp.online.clickup.task.entity.TaskDependency;
import uz.pdp.online.clickup.common.enums.TaskHistoryType;
import uz.pdp.online.clickup.common.exceptions.AlreadyExistsException;
import uz.pdp.online.clickup.common.exceptions.NotFoundException;
import uz.pdp.online.clickup.task.mapper.TaskDependencyMapper;
import uz.pdp.online.clickup.task.dto.TaskDependencyRequestDto;
import uz.pdp.online.clickup.task.dto.TaskDependencyResponseDto;
import uz.pdp.online.clickup.task.repository.TaskDependencyRepository;
import uz.pdp.online.clickup.task.repository.TaskRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskDependencyService {

    private final TaskDependencyRepository taskDependencyRepository;
    private final TaskRepository taskRepository;
    private final TaskHistoryService taskHistoryService;
    private final TaskDependencyMapper taskDependencyMapper;

    @Transactional
    public TaskDependencyResponseDto create(TaskDependencyRequestDto dto) {
        log.debug("Request to create Task Dependency started. Task ID: {}, Dependency Task ID: {}",
                dto.getTaskId(), dto.getDependencyTaskId());

        Task task = taskRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new NotFoundException("Task not found with ID: " + dto.getTaskId()));

        Task dependencyTask = taskRepository.findById(dto.getDependencyTaskId())
                .orElseThrow(() -> new NotFoundException("Dependency Task not found with ID: " + dto.getDependencyTaskId()));

        if (dto.getTaskId().equals(dto.getDependencyTaskId())) {
            throw new IllegalArgumentException("Task cannot depend on itself");
        }

        if (taskDependencyRepository.existsByTaskIdAndDependencyTaskId(dto.getTaskId(), dto.getDependencyTaskId())) {
            throw new AlreadyExistsException("Dependency already exists between Task " + dto.getTaskId()
                    + " and Task " + dto.getDependencyTaskId());
        }

        TaskDependency taskDependency = new TaskDependency();
        taskDependency.setTask(task);
        taskDependency.setDependencyTask(dependencyTask);
        taskDependency.setDependencyType(dto.getDependencyType());

        TaskDependency saved = taskDependencyRepository.save(taskDependency);

        taskHistoryService.logChange(task, "dependency", null, dependencyTask.getName(), TaskHistoryType.ADD);
        log.info("Task Dependency created successfully. ID: {}", saved.getId());

        return taskDependencyMapper.toResponseDto(saved);
    }

    @Transactional
    public void delete(UUID id) {
        log.debug("Request to delete Task Dependency started. ID: {}", id);

        TaskDependency dependency = taskDependencyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task Dependency not found with ID: " + id));

        taskHistoryService.logChange(dependency.getTask(), "dependency", dependency.getDependencyTask().getName(),
                null, TaskHistoryType.DELETE);
        taskDependencyRepository.delete(dependency);

        log.info("Task Dependency deleted successfully. ID: {}", id);
    }

    @Transactional(readOnly = true)
    public List<TaskDependencyResponseDto> getByTaskId(UUID taskId) {
        log.debug("Request to get Task Dependencies by Task ID: {}", taskId);

        List<TaskDependency> dependencies = taskDependencyRepository.findAllByTaskId(taskId);

        return taskDependencyMapper.toResponseDtoList(dependencies);
    }
}
