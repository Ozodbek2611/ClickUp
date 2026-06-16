package uz.pdp.online.clickup.model.taskTagDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.online.clickup.model.tagDto.TagResponseDto;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskTagResponseDto {

    private UUID id;

    private UUID taskId;

    private TagResponseDto tag;

    private Timestamp createdAt;
}
