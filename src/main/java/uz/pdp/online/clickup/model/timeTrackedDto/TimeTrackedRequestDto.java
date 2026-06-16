package uz.pdp.online.clickup.model.timeTrackedDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
public class TimeTrackedRequestDto {

    @NotNull(message = "Task ID cannot be null")
    private UUID taskId;
}
