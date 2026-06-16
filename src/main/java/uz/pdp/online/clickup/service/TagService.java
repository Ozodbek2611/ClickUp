package uz.pdp.online.clickup.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.online.clickup.entity.Tag;
import uz.pdp.online.clickup.entity.Task;
import uz.pdp.online.clickup.entity.TaskTag;
import uz.pdp.online.clickup.entity.Workspace;
import uz.pdp.online.clickup.exceptions.AlreadyExistsException;
import uz.pdp.online.clickup.exceptions.NotFoundException;
import uz.pdp.online.clickup.mapper.TagMapper;
import uz.pdp.online.clickup.mapper.TaskTagMapper;
import uz.pdp.online.clickup.model.tagDto.TagRequestDto;
import uz.pdp.online.clickup.model.tagDto.TagResponseDto;
import uz.pdp.online.clickup.model.taskTagDto.TaskTagRequestDto;
import uz.pdp.online.clickup.model.taskTagDto.TaskTagResponseDto;
import uz.pdp.online.clickup.repository.TagRepository;
import uz.pdp.online.clickup.repository.TaskRepository;
import uz.pdp.online.clickup.repository.TaskTagRepository;
import uz.pdp.online.clickup.repository.WorkspaceRepository;

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
            throw new AlreadyExistsException(
                    String.format("Tag [ID: %s] is already assigned to Task [ID: %s]", dto.getTagId(), dto.getTaskId())
            );
        }

        TaskTag taskTag = new TaskTag();
        taskTag.setTask(task);
        taskTag.setTag(tag);

        TaskTag savedTaskTag = taskTagRepository.save(taskTag);
        log.info("Tag successfully added to Task. TaskTag ID: {}, Task ID: {}, Tag ID: {}",
                savedTaskTag.getId(), dto.getTaskId(), dto.getTagId());

        return taskTagMapper.toResponseDto(savedTaskTag);
    }

    @Transactional
    public void removeFromTask(UUID taskId, UUID tagId) {
        log.debug("Request to remove Tag from Task started. Task ID: {}, Tag ID: {}", taskId, tagId);

        taskTagRepository.findByTaskIdAndTagId(taskId, tagId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Tag [ID: %s] not found on Task [ID: %s]", tagId, taskId)
                ));

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
