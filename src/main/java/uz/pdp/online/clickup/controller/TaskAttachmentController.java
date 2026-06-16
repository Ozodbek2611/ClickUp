package uz.pdp.online.clickup.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.model.ApiResponse;
import uz.pdp.online.clickup.model.taskAttachmentDto.TaskAttachmentRequestDto;
import uz.pdp.online.clickup.model.taskAttachmentDto.TaskAttachmentResponseDto;
import uz.pdp.online.clickup.service.TaskAttachmentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/task-attachment")
@RequiredArgsConstructor
@Slf4j
public class TaskAttachmentController {

    private final TaskAttachmentService taskAttachmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<TaskAttachmentResponseDto>> add(@Valid @RequestBody TaskAttachmentRequestDto dto) {
        log.info("REST request to attach Attachment ID: {} to Task ID: {}", dto.getAttachmentId(), dto.getTaskId());
        TaskAttachmentResponseDto response = taskAttachmentService.add(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(response, "Attachment attached to task successfully"));
    }

    @DeleteMapping("/{taskAttachmentId}")
    public ResponseEntity<ApiResponse<Void>> remove(@PathVariable UUID taskAttachmentId) {
        log.info("REST request to remove TaskAttachment ID: {}", taskAttachmentId);
        taskAttachmentService.remove(taskAttachmentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(null, "Attachment removed from task successfully"));
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<ApiResponse<List<TaskAttachmentResponseDto>>> getByTask(@PathVariable UUID taskId) {
        log.info("REST request to get all Attachments for Task ID: {}", taskId);
        List<TaskAttachmentResponseDto> response = taskAttachmentService.getByTaskId(taskId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(response, "Attachments fetched successfully"));
    }
}
