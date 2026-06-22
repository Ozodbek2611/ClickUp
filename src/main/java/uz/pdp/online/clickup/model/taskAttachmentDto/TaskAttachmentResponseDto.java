package uz.pdp.online.clickup.model.taskAttachmentDto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Task-attachment link details")
public class TaskAttachmentResponseDto {

    @Schema(description = "Unique identifier of the link", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "ID of the task", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID taskId;

    @Schema(description = "ID of the linked attachment", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID attachmentId;

    @Schema(description = "Whether this is pinned as cover image", example = "false")
    private Boolean pinCoverImage;

    @Schema(description = "Timestamp when the link was created")
    private Timestamp createdAt;

    @Schema(description = "Timestamp when the link was last updated")
    private Timestamp updatedAt;
}
