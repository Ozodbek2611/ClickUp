package uz.pdp.online.clickup.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.online.clickup.entity.Comment;
import uz.pdp.online.clickup.entity.Task;
import uz.pdp.online.clickup.entity.enums.TaskHistoryType;
import uz.pdp.online.clickup.exceptions.NotFoundException;
import uz.pdp.online.clickup.mapper.CommentMapper;
import uz.pdp.online.clickup.model.commentDto.CommentRequestDto;
import uz.pdp.online.clickup.model.commentDto.CommentResponseDto;
import uz.pdp.online.clickup.repository.CommentRepository;
import uz.pdp.online.clickup.repository.TaskRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final TaskHistoryService taskHistoryService;
    private final CommentMapper commentMapper;

    public CommentResponseDto add(CommentRequestDto dto) {
        log.debug("Request to add Comment started for Task ID: {}", dto.getTaskId());

        Task task = taskRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new NotFoundException("Task not found with ID: " + dto.getTaskId()));

        Comment comment = new Comment();
        comment.setName(dto.getName());
        comment.setTask(task);

        Comment savedComment = commentRepository.save(comment);
        taskHistoryService.logChange(task, "comment", null, savedComment.getName(), TaskHistoryType.ADD);
        log.info("Comment successfully added. ID: {}, Task ID: {}", savedComment.getId(), dto.getTaskId());

        return commentMapper.toResponseDto(savedComment);
    }

    public CommentResponseDto edit(UUID commentId, String newText) {
        log.debug("Request to edit Comment started. ID: {}", commentId);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment not found with ID: " + commentId));

        comment.setName(newText);
        Comment updatedComment = commentRepository.save(comment);

        log.info("Comment content successfully updated. ID: {}", commentId);
        return commentMapper.toResponseDto(updatedComment);
    }

    public void delete(UUID commentId) {
        log.debug("Request to delete Comment started. ID: {}", commentId);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment not found with ID: " + commentId));

        taskHistoryService.logChange(comment.getTask(), "comment", comment.getName(), null, TaskHistoryType.DELETE);
        commentRepository.delete(comment);
        log.info("Comment successfully deleted. ID: {}", commentId);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getByTaskId(UUID taskId) {
        log.debug("Request to fetch Comments for Task ID: {}", taskId);
        List<Comment> comments = commentRepository.findAllByTaskId(taskId);
        return commentMapper.toResponseDtoList(comments);
    }
}
