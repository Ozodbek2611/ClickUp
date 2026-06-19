package uz.pdp.online.clickup.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.common.ApiResponseDto;
import uz.pdp.online.clickup.model.taskAttachmentDto.TaskAttachmentRequestDto;
import uz.pdp.online.clickup.model.taskAttachmentDto.TaskAttachmentResponseDto;
import uz.pdp.online.clickup.service.TaskAttachmentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/task-attachment")
@RequiredArgsConstructor
@Tag(name = "Task Attachment", description = "Task Attachment APIs")
public class TaskAttachmentController {

    private final TaskAttachmentService taskAttachmentService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<TaskAttachmentResponseDto>> add(@Valid @RequestBody TaskAttachmentRequestDto dto) {
        TaskAttachmentResponseDto response = taskAttachmentService.add(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(response, "Attachment attached to task successfully"));
    }

    @DeleteMapping("/{taskAttachmentId}")
    public ResponseEntity<ApiResponseDto<Void>> remove(@PathVariable UUID taskAttachmentId) {
        taskAttachmentService.remove(taskAttachmentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "Attachment removed from task successfully"));
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<ApiResponseDto<List<TaskAttachmentResponseDto>>> getByTask(@PathVariable UUID taskId) {
        List<TaskAttachmentResponseDto> response = taskAttachmentService.getByTaskId(taskId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(response, "Attachments fetched successfully"));
    }
}
