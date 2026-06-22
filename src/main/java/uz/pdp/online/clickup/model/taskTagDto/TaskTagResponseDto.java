package uz.pdp.online.clickup.model.taskTagDto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.online.clickup.model.tagDto.TagResponseDto;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Task-tag association details")
public class TaskTagResponseDto {

    @Schema(description = "Unique identifier of the association", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "ID of the task", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID taskId;

    @Schema(description = "Full tag details")
    private TagResponseDto tag;

    @Schema(description = "Timestamp when the tag was added to the task")
    private Timestamp createdAt;
}
