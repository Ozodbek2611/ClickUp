package uz.pdp.online.clickup.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.online.clickup.category.entity.Category;
import uz.pdp.online.clickup.priority.entity.Priority;
import uz.pdp.online.clickup.status.entity.Status;
import uz.pdp.online.clickup.task.entity.Task;
import uz.pdp.online.clickup.common.exceptions.NotFoundException;
import uz.pdp.online.clickup.task.mapper.TaskMapper;
import uz.pdp.online.clickup.task.dto.TaskRequestDto;
import uz.pdp.online.clickup.task.dto.TaskResponseDto;
import uz.pdp.online.clickup.category.repository.CategoryRepository;
import uz.pdp.online.clickup.priority.repository.PriorityRepository;
import uz.pdp.online.clickup.status.repository.StatusRepository;
import uz.pdp.online.clickup.task.repository.TaskRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;
    private final StatusRepository statusRepository;
    private final CategoryRepository categoryRepository;
    private final PriorityRepository priorityRepository;
    private final TaskMapper taskMapper;

    @Transactional
    public TaskResponseDto create(TaskRequestDto dto) {
        log.debug("Creating task: {}", dto.getName());

        Status status = statusRepository.findById(dto.getStatusId())
                .orElseThrow(() -> new NotFoundException("Status not found: " + dto.getStatusId()));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found: " + dto.getCategoryId()));

        Task task = new Task();
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setStatus(status);
        task.setCategory(category);
        task.setStartedDate(dto.getStartedDate());
        task.setStartTimeHas(dto.getStartTimeHas());
        task.setDueDate(dto.getDueDate());
        task.setDueTimeHas(dto.getDueTimeHas());
        task.setEstimateTime(dto.getEstimateTime());
        task.setActivatedDate(dto.getActivatedDate());

        if (dto.getPriorityId() != null) {
            Priority priority = priorityRepository.findById(dto.getPriorityId())
                    .orElseThrow(() -> new NotFoundException("Priority not found: " + dto.getPriorityId()));
            task.setPriority(priority);
        }

        if (dto.getParentTaskId() != null) {
            Task parentTask = taskRepository.findById(dto.getParentTaskId())
                    .orElseThrow(() -> new NotFoundException("Parent task not found: " + dto.getParentTaskId()));
            task.setParentTask(parentTask);
        }

        Task saved = taskRepository.save(task);
        log.info("Task created. ID: {}, Name: {}", saved.getId(), saved.getName());
        return taskMapper.toResponseDto(saved);
    }

    @Transactional
    public TaskResponseDto changeStatus(UUID taskId, UUID statusId) {
        log.debug("Changing status. Task: {}, Status: {}", taskId, statusId);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found: " + taskId));

        Status status = statusRepository.findById(statusId)
                .orElseThrow(() -> new NotFoundException("Status not found: " + statusId));

        task.setStatus(status);
        Task updated = taskRepository.save(task);

        log.info("Task status updated. Task: {}, Status: {}", taskId, status.getName());
        return taskMapper.toResponseDto(updated);
    }

    public List<TaskResponseDto> getByCategoryId(UUID categoryId) {
        log.debug("Fetching tasks by category: {}", categoryId);
        return taskMapper.toResponseDtoList(taskRepository.findAllByCategoryId(categoryId));
    }
}
