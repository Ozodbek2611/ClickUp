package uz.pdp.online.clickup.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request body for linking an attachment to a task")
public class TaskAttachmentRequestDto {

    @NotNull(message = "Task ID is required")
    @Schema(description = "ID of the task", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID taskId;

    @NotNull(message = "Attachment ID is required")
    @Schema(description = "ID of the attachment to link", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID attachmentId;

    @Schema(description = "Whether to pin this attachment as the task cover image", example = "false")
    private Boolean pinCoverImage = false;
}
