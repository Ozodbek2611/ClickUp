package uz.pdp.online.clickup.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.online.clickup.entity.domain.Attachment;
import uz.pdp.online.clickup.entity.domain.Task;
import uz.pdp.online.clickup.entity.relation.TaskAttachment;
import uz.pdp.online.clickup.entity.enums.TaskHistoryType;
import uz.pdp.online.clickup.exceptions.NotFoundException;
import uz.pdp.online.clickup.mapper.TaskAttachmentMapper;
import uz.pdp.online.clickup.model.taskAttachmentDto.TaskAttachmentRequestDto;
import uz.pdp.online.clickup.model.taskAttachmentDto.TaskAttachmentResponseDto;
import uz.pdp.online.clickup.repository.AttachmentRepository;
import uz.pdp.online.clickup.repository.TaskAttachmentRepository;
import uz.pdp.online.clickup.repository.TaskRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskAttachmentService {

    private final TaskAttachmentRepository taskAttachmentRepository;
    private final TaskRepository taskRepository;
    private final AttachmentRepository attachmentRepository;
    private final TaskHistoryService taskHistoryService;
    private final TaskAttachmentMapper taskAttachmentMapper;

    public TaskAttachmentResponseDto add(TaskAttachmentRequestDto dto) {
        log.debug("Request to attach Attachment to Task started. Task ID: {}, Attachment ID: {}",
                dto.getTaskId(), dto.getAttachmentId());

        Task task = taskRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new NotFoundException("Task not found with ID: " + dto.getTaskId()));

        Attachment attachment = attachmentRepository.findById(dto.getAttachmentId())
                .orElseThrow(() -> new NotFoundException("Attachment not found with ID: " + dto.getAttachmentId()));

        TaskAttachment taskAttachment = new TaskAttachment();
        taskAttachment.setTask(task);
        taskAttachment.setAttachment(attachment);
        taskAttachment.setPinCoverImage(dto.getPinCoverImage() != null && dto.getPinCoverImage());

        taskHistoryService.logChange(task, "attachment", null, attachment.getName(), TaskHistoryType.ADD);
        TaskAttachment saved = taskAttachmentRepository.save(taskAttachment);
        log.info("Attachment successfully attached to Task. TaskAttachment ID: {}, Task ID: {}", saved.getId(), dto.getTaskId());

        return taskAttachmentMapper.toResponseDto(saved);
    }

    public void remove(UUID taskAttachmentId) {
        log.debug("Request to remove TaskAttachment started. ID: {}", taskAttachmentId);

        TaskAttachment taskAttachment = taskAttachmentRepository.findById(taskAttachmentId)
                .orElseThrow(() -> new NotFoundException("TaskAttachment not found with ID: " + taskAttachmentId));

        taskHistoryService.logChange(taskAttachment.getTask(), "attachment", taskAttachment.getAttachment().getName(),
                null, TaskHistoryType.DELETE);
        taskAttachmentRepository.delete(taskAttachment);
        log.info("TaskAttachment successfully removed. ID: {}", taskAttachmentId);
    }

    @Transactional(readOnly = true)
    public List<TaskAttachmentResponseDto> getByTaskId(UUID taskId) {
        log.debug("Request to fetch TaskAttachments for Task ID: {}", taskId);
        List<TaskAttachment> attachments = taskAttachmentRepository.findAllByTaskId(taskId);
        return taskAttachmentMapper.toResponseDtoList(attachments);
    }
}
