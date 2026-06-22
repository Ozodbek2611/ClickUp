package uz.pdp.online.clickup.model.taskUserDto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request body for assigning a user to a task")
public class TaskUserRequestDto {

    @NotNull(message = "Task ID is required")
    @Schema(description = "ID of the task", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID taskId;

    @NotNull(message = "User ID is required")
    @Schema(description = "ID of the user to assign", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID userId;
}
