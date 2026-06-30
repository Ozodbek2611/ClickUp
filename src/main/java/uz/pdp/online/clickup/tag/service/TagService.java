package uz.pdp.online.clickup.tag.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.online.clickup.tag.entity.Tag;
import uz.pdp.online.clickup.task.entity.Task;
import uz.pdp.online.clickup.tag.entity.TaskTag;
import uz.pdp.online.clickup.workspace.entity.Workspace;
import uz.pdp.online.clickup.common.enums.TaskHistoryType;
import uz.pdp.online.clickup.common.exceptions.AlreadyExistsException;
import uz.pdp.online.clickup.common.exceptions.NotFoundException;
import uz.pdp.online.clickup.tag.mapper.TagMapper;
import uz.pdp.online.clickup.tag.mapper.TaskTagMapper;
import uz.pdp.online.clickup.tag.dto.TagRequestDto;
import uz.pdp.online.clickup.tag.dto.TagResponseDto;
import uz.pdp.online.clickup.tag.dto.TaskTagRequestDto;
import uz.pdp.online.clickup.tag.dto.TaskTagResponseDto;
import uz.pdp.online.clickup.tag.repository.TagRepository;
import uz.pdp.online.clickup.task.repository.TaskRepository;
import uz.pdp.online.clickup.tag.repository.TaskTagRepository;
import uz.pdp.online.clickup.workspace.repository.WorkspaceRepository;
import uz.pdp.online.clickup.audit.service.TaskHistoryService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagService {

    private final TagRepository tagRepository;
    private final TaskRepository taskRepository;
    private final TaskTagRepository taskTagRepository;
    private final WorkspaceRepository workspaceRepository;
    private final TaskHistoryService taskHistoryService;
    private final TagMapper tagMapper;
    private final TaskTagMapper taskTagMapper;

    public TagResponseDto create(TagRequestDto dto) {
        log.debug("Request to create Tag started. Name: {}, Workspace ID: {}", dto.getName(), dto.getWorkspaceId());

        Workspace workspace = workspaceRepository.findById(dto.getWorkspaceId())
                .orElseThrow(() -> new NotFoundException("Workspace not found with ID: " + dto.getWorkspaceId()));

        Tag tag = new Tag();
        tag.setName(dto.getName());
        tag.setColor(dto.getColor());
        tag.setWorkspace(workspace);

        Tag savedTag = tagRepository.save(tag);
        log.info("Tag successfully created. ID: {}, Name: {}", savedTag.getId(), savedTag.getName());
        return tagMapper.toResponseDto(savedTag);
    }

    public TaskTagResponseDto addToTask(TaskTagRequestDto dto) {
        log.debug("Request to add Tag to Task started. Task ID: {}, Tag ID: {}", dto.getTaskId(), dto.getTagId());

        Task task = taskRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new NotFoundException("Task not found with ID: " + dto.getTaskId()));

        Tag tag = tagRepository.findById(dto.getTagId())
                .orElseThrow(() -> new NotFoundException("Tag not found with ID: " + dto.getTagId()));

        if (taskTagRepository.findByTaskIdAndTagId(dto.getTaskId(), dto.getTagId()).isPresent()) {
            throw new AlreadyExistsException("Tag is already assigned to Task" + dto.getTagId());
        }

        TaskTag taskTag = new TaskTag();
        taskTag.setTask(task);
        taskTag.setTag(tag);

        TaskTag savedTaskTag = taskTagRepository.save(taskTag);
        taskHistoryService.logChange(task, "tag", null, tag.getName(), TaskHistoryType.ADD);
        log.info("Tag successfully added to Task. TaskTag ID: {}, Task ID: {}, Tag ID: {}",
                savedTaskTag.getId(), dto.getTaskId(), dto.getTagId());

        return taskTagMapper.toResponseDto(savedTaskTag);
    }

    @Transactional
    public void removeFromTask(UUID taskId, UUID tagId) {
        log.debug("Request to remove Tag from Task started. Task ID: {}, Tag ID: {}", taskId, tagId);

        TaskTag taskTag = taskTagRepository.findByTaskIdAndTagId(taskId, tagId)
                .orElseThrow(() -> new NotFoundException("Task not found with ID: " + taskId));

        taskHistoryService.logChange(taskTag.getTask(), "tag", taskTag.getTag().getName(), null, TaskHistoryType.DELETE);

        taskTagRepository.deleteByTaskIdAndTagId(taskId, tagId);
        log.info("Tag successfully removed from Task. Task ID: {}, Tag ID: {}", taskId, tagId);
    }

    @Transactional(readOnly = true)
    public List<TagResponseDto> getByWorkspaceId(Long workspaceId) {
        log.debug("Request to fetch Tags for Workspace ID: {}", workspaceId);
        List<Tag> tags = tagRepository.findAllByWorkspaceId(workspaceId);
        return tagMapper.toResponseDtoList(tags);
    }
}
