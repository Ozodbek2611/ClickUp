package uz.pdp.online.clickup.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.online.clickup.entity.Category;
import uz.pdp.online.clickup.entity.Priority;
import uz.pdp.online.clickup.entity.Status;
import uz.pdp.online.clickup.entity.Task;
import uz.pdp.online.clickup.exceptions.NotFoundException;
import uz.pdp.online.clickup.mapper.TaskMapper;
import uz.pdp.online.clickup.model.taskDto.TaskRequestDto;
import uz.pdp.online.clickup.model.taskDto.TaskResponseDto;
import uz.pdp.online.clickup.repository.CategoryRepository;
import uz.pdp.online.clickup.repository.PriorityRepository;
import uz.pdp.online.clickup.repository.StatusRepository;
import uz.pdp.online.clickup.repository.TaskRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;
    private final StatusRepository statusRepository;
    private final CategoryRepository categoryRepository;
    private final PriorityRepository priorityRepository;
    private final TaskMapper taskMapper;

    public TaskResponseDto create(TaskRequestDto dto) {
        log.debug("Request to create Task started with name: {}", dto.getName());

        Status status = statusRepository.findById(dto.getStatusId())
                .orElseThrow(() -> new NotFoundException("Status not found with ID: " + dto.getStatusId()));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found with ID: " + dto.getCategoryId()));

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
                    .orElseThrow(() -> new NotFoundException("Priority not found with ID: " + dto.getPriorityId()));
            task.setPriority(priority);
        }

        if (dto.getParentTaskId() != null) {
            Task parentTask = taskRepository.findById(dto.getParentTaskId())
                    .orElseThrow(() -> new NotFoundException("Parent Task not found with ID: " + dto.getParentTaskId()));
            task.setParentTask(parentTask);
        }

        Task savedTask = taskRepository.save(task);
        log.info("Task successfully created. ID: {}, Name: {}", savedTask.getId(), savedTask.getName());
        return taskMapper.toResponseDto(savedTask);
    }

    public TaskResponseDto changeStatus(UUID taskId, UUID statusId) {
        log.debug("Request to change Task status. Task ID: {}, New Status ID: {}", taskId, statusId);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found with ID: " + taskId));

        Status status = statusRepository.findById(statusId)
                .orElseThrow(() -> new NotFoundException("Status not found with ID: " + statusId));

        task.setStatus(status);
        Task updatedTask = taskRepository.save(task);

        log.info("Task status successfully updated. Task ID: {}, Status: {}", taskId, status.getName());
        return taskMapper.toResponseDto(updatedTask);
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDto> getByCategoryId(UUID categoryId) {
        log.debug("Request to fetch Tasks by Category ID: {}", categoryId);
        List<Task> tasks = taskRepository.findAllByCategoryId(categoryId);
        return taskMapper.toResponseDtoList(tasks);
    }
}
