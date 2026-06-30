package uz.pdp.online.clickup.timeTracked.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
@Schema(description = "Request body for starting a time tracking session")
public class TimeTrackedRequestDto {

    @NotNull(message = "Task ID cannot be null")
    @Schema(description = "ID of the task to track time for", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID taskId;
}
