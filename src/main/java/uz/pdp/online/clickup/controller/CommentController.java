package uz.pdp.online.clickup.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.common.ApiResponseDto;
import uz.pdp.online.clickup.model.commentDto.CommentRequestDto;
import uz.pdp.online.clickup.model.commentDto.CommentResponseDto;
import uz.pdp.online.clickup.service.CommentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@Tag(name = "Comment", description = "Comment APIs")
public class CommentController {

    private final CommentService commentService;

        @Operation(summary = "Add comment", description = "Adds a new comment to a task")

    @PostMapping
    public ResponseEntity<ApiResponseDto<CommentResponseDto>> add(@RequestBody CommentRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(commentService.add(dto), "Comment added successfully"));
    }

    @PatchMapping("/{commentId}")
    @Operation(summary = "Edit comment", description = "Updates the text of an existing comment")
    public ResponseEntity<ApiResponseDto<CommentResponseDto>> edit(@PathVariable UUID commentId,
                                                                @RequestParam String text) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(commentService.edit(commentId, text), "Comment updated successfully"));
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "Delete comment", description = "Permanently deletes a comment")
    public ResponseEntity<ApiResponseDto<Void>> delete(@PathVariable UUID commentId) {
        commentService.delete(commentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "Comment deleted successfully"));
    }

        @Operation(summary = "Get comments by task", description = "Returns all comments for a specific task")

    @GetMapping("/task/{taskId}")
    public ResponseEntity<ApiResponseDto<List<CommentResponseDto>>> getByTask(@PathVariable UUID taskId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(commentService.getByTaskId(taskId), "Comments fetched successfully"));
    }
}
