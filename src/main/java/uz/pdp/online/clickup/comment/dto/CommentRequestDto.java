package uz.pdp.online.clickup.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request body for adding a comment to a task")
public class CommentRequestDto {

    @NotBlank(message = "Comment text cannot be blank")
    @Schema(description = "Text content of the comment", example = "This task needs more details")
    private String name;

    @NotNull(message = "Task ID is required")
    @Schema(description = "ID of the task to comment on", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID taskId;
}
