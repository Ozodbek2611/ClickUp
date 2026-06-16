package uz.pdp.online.clickup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.model.ApiResponse;
import uz.pdp.online.clickup.model.commentDto.CommentRequestDto;
import uz.pdp.online.clickup.model.commentDto.CommentResponseDto;
import uz.pdp.online.clickup.service.CommentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponseDto>> add(@RequestBody CommentRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(commentService.add(dto), "Comment added successfully"));
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponseDto>> edit(@PathVariable UUID commentId,
                                                                @RequestParam String text) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(commentService.edit(commentId, text), "Comment updated successfully"));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID commentId) {
        commentService.delete(commentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(null, "Comment deleted successfully"));
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<ApiResponse<List<CommentResponseDto>>> getByTask(@PathVariable UUID taskId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(commentService.getByTaskId(taskId), "Comments fetched successfully"));
    }
}
