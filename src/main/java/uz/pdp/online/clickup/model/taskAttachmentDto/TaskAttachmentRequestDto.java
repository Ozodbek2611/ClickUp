package uz.pdp.online.clickup.model.taskAttachmentDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskAttachmentRequestDto {

    @NotNull(message = "Task ID is required")
    private UUID taskId;

    @NotNull(message = "Attachment ID is required")
    private UUID attachmentId;

    private Boolean pinCoverImage = false;
}
