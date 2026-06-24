package uz.pdp.online.clickup.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Attach file to task", description = "Links an existing attachment to a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Attachment attached to task successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Attachment attached to task successfully\",\n  \"data\": {\n    \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n    \"taskId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa7\",\n    \"attachmentId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa8\"\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "400", description = "Validation Failed",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Validation Failed\",\n  \"data\": null,\n  \"errors\": [\"taskId: must not be null\"]\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Task or attachment not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Task not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa7\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<TaskAttachmentResponseDto>> add(@Valid @RequestBody TaskAttachmentRequestDto dto) {
        TaskAttachmentResponseDto response = taskAttachmentService.add(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(response, "Attachment attached to task successfully"));
    }

    @DeleteMapping("/{taskAttachmentId}")
    @Operation(summary = "Remove attachment from task", description = "Removes an attachment link from a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Attachment removed from task successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Attachment removed from task successfully\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "TaskAttachment not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"TaskAttachment not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<Void>> remove(@PathVariable UUID taskAttachmentId) {
        taskAttachmentService.remove(taskAttachmentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "Attachment removed from task successfully"));
    }

    @Operation(summary = "Get task attachments", description = "Returns all attachments linked to a specific task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Attachments fetched successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Attachments fetched successfully\",\n  \"data\": [\n    {\n      \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n      \"taskId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa7\",\n      \"attachmentId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa8\"\n    }\n  ],\n  \"errors\": null\n}")
                    ))
    })
    @GetMapping("/task/{taskId}")
    public ResponseEntity<ApiResponseDto<List<TaskAttachmentResponseDto>>> getByTask(@PathVariable UUID taskId) {
        List<TaskAttachmentResponseDto> response = taskAttachmentService.getByTaskId(taskId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(response, "Attachments fetched successfully"));
    }
}
