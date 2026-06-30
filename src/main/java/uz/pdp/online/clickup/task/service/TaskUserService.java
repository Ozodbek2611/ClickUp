package uz.pdp.online.clickup.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.online.clickup.audit.service.TaskHistoryService;
import uz.pdp.online.clickup.task.entity.Task;
import uz.pdp.online.clickup.task.entity.TaskUser;
import uz.pdp.online.clickup.user.entity.User;
import uz.pdp.online.clickup.common.enums.TaskHistoryType;
import uz.pdp.online.clickup.common.exceptions.AlreadyExistsException;
import uz.pdp.online.clickup.common.exceptions.NotFoundException;
import uz.pdp.online.clickup.task.mapper.TaskUserMapper;
import uz.pdp.online.clickup.task.dto.TaskUserRequestDto;
import uz.pdp.online.clickup.task.dto.TaskUserResponseDto;
import uz.pdp.online.clickup.task.repository.TaskRepository;
import uz.pdp.online.clickup.task.repository.TaskUserRepository;
import uz.pdp.online.clickup.user.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskUserService {

    private final TaskUserRepository taskUserRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskHistoryService taskHistoryService;
    private final TaskUserMapper taskUserMapper;

    public TaskUserResponseDto assign(TaskUserRequestDto dto) {
        log.debug("Request to assign User to Task started. Task ID: {}, User ID: {}", dto.getTaskId(), dto.getUserId());

        Task task = taskRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new NotFoundException("Task not found with ID: " + dto.getTaskId()));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + dto.getUserId()));

        if (taskUserRepository.findByTaskIdAndUserId(dto.getTaskId(), dto.getUserId()).isPresent()) {
            throw new AlreadyExistsException(
                    String.format("User [ID: %s] is already assigned to Task [ID: %s]", dto.getUserId(), dto.getTaskId())
            );
        }

        TaskUser taskUser = new TaskUser();
        taskUser.setTask(task);
        taskUser.setUser(user);

        TaskUser saved = taskUserRepository.save(taskUser);
        taskHistoryService.logChange(task, "assignee", null, user.getEmail(), TaskHistoryType.ADD);
        log.info("User successfully assigned to Task. TaskUser ID: {}, Task ID: {}, User ID: {}",
                saved.getId(), dto.getTaskId(), dto.getUserId());

        return taskUserMapper.toResponseDto(saved);
    }

    @Transactional
    public void unassign(UUID taskId, UUID userId) {
        log.debug("Request to unassign User from Task started. Task ID: {}, User ID: {}", taskId, userId);

        TaskUser taskUser = taskUserRepository.findByTaskIdAndUserId(taskId, userId)
                .orElseThrow(() -> new NotFoundException("User is not assigned to Task" + userId));

        taskHistoryService.logChange(taskUser.getTask(), "assignee", taskUser.getUser().getEmail(), null, TaskHistoryType.DELETE);
        taskUserRepository.deleteByTaskIdAndUserId(taskId, userId);
        log.info("User successfully unassigned from Task. Task ID: {}, User ID: {}", taskId, userId);
    }

    @Transactional(readOnly = true)
    public List<TaskUserResponseDto> getByTaskId(UUID taskId) {
        log.debug("Request to fetch assigned users for Task ID: {}", taskId);
        List<TaskUser> taskUsers = taskUserRepository.findAllByTaskId(taskId);
        return taskUserMapper.toResponseDtoList(taskUsers);
    }
}
