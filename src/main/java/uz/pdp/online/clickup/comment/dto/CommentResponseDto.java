package uz.pdp.online.clickup.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Comment details returned from the API")
public class CommentResponseDto {

    @Schema(description = "Unique identifier of the comment", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "Text content of the comment", example = "This task needs more details")
    private String name;

    @Schema(description = "ID of the task this comment belongs to", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID taskId;

    @Schema(description = "Timestamp when the comment was created")
    private Timestamp createdAt;

    @Schema(description = "Timestamp when the comment was last updated")
    private Timestamp updatedAt;
}

