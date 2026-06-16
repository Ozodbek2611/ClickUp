package uz.pdp.online.clickup.model.taskTagDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskTagRequestDto {

    @NotNull(message = "Task ID is required")
    private UUID taskId;

    @NotNull(message = "Tag ID is required")
    private UUID tagId;
}
