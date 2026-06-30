package uz.pdp.online.clickup.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.common.ApiResponseDto;
import uz.pdp.online.clickup.comment.dto.CommentRequestDto;
import uz.pdp.online.clickup.comment.dto.CommentResponseDto;
import uz.pdp.online.clickup.comment.service.CommentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@Tag(name = "Comment", description = "Comment APIs")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "Add comment", description = "Adds a new comment to a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment added successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Comment added successfully\",\n  \"data\": {\n    \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n    \"name\": \"Looks good to me\"\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Task not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<CommentResponseDto>> add(@RequestBody CommentRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(commentService.add(dto), "Comment added successfully"));
    }

    @PatchMapping("/{commentId}")
    @Operation(summary = "Edit comment", description = "Updates the text of an existing comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Comment updated successfully\",\n  \"data\": {\n    \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n    \"name\": \"Updated comment text\"\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Comment not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Comment not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<CommentResponseDto>> edit(@PathVariable UUID commentId,
                                                                   @RequestParam String text) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(commentService.edit(commentId, text), "Comment updated successfully"));
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "Delete comment", description = "Permanently deletes a comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment deleted successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Comment deleted successfully\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Comment not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Comment not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<Void>> delete(@PathVariable UUID commentId) {
        commentService.delete(commentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "Comment deleted successfully"));
    }

    @Operation(summary = "Get comments by task", description = "Returns all comments for a specific task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments fetched successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Comments fetched successfully\",\n  \"data\": [\n    {\n      \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n      \"name\": \"Looks good to me\"\n    }\n  ],\n  \"errors\": null\n}")
                    ))
    })
    @GetMapping("/task/{taskId}")
    public ResponseEntity<ApiResponseDto<List<CommentResponseDto>>> getByTask(@PathVariable UUID taskId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(commentService.getByTaskId(taskId), "Comments fetched successfully"));
    }
}
