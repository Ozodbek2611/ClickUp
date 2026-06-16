package uz.pdp.online.clickup.model.timeTrackedDto;

import lombok.Data;
import java.sql.Timestamp;
import java.util.UUID;

@Data
public class TimeTrackedResponseDto {

    private UUID id;

    private UUID taskId;

    private Timestamp startedAt;

    private Timestamp stoppedAt;
}
