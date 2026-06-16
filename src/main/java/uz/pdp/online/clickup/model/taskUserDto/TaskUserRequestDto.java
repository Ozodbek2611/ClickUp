package uz.pdp.online.clickup.model.taskUserDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskUserRequestDto {

    @NotNull(message = "Task ID is required")
    private UUID taskId;

    @NotNull(message = "User ID is required")
    private UUID userId;
}
