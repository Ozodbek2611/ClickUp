package uz.pdp.online.clickup.model.commentDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {

    private UUID id;

    private String name;

    private UUID taskId;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}

