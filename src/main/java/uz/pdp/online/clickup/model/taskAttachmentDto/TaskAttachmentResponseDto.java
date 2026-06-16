package uz.pdp.online.clickup.model.taskAttachmentDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskAttachmentResponseDto {

    private UUID id;

    private UUID taskId;

    private UUID attachmentId;

    private Boolean pinCoverImage;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
