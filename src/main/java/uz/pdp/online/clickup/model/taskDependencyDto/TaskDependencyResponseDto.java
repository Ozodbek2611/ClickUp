package uz.pdp.online.clickup.model.taskDependencyDto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.online.clickup.entity.enums.DependencyType;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Task dependency details")
public class TaskDependencyResponseDto {

    @Schema(description = "Unique identifier of the dependency", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "ID of the main task", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID taskId;

    @Schema(description = "ID of the dependency task", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID dependencyTaskId;

    @Schema(description = "Type of dependency relationship", example = "BLOCKING")
    private DependencyType dependencyType;

    @Schema(description = "Timestamp when the dependency was created")
    private Timestamp createdAt;
}
