package uz.pdp.online.clickup.model.timeTrackedDto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Schema(description = "Time tracking session details")
public class TimeTrackedResponseDto {

    @Schema(description = "Unique identifier of the session", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "ID of the tracked task", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID taskId;

    @Schema(description = "Timestamp when tracking started")
    private Timestamp startedAt;

    @Schema(description = "Timestamp when tracking stopped (null if still running)")
    private Timestamp stoppedAt;
}
