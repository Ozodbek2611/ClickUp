package uz.pdp.online.clickup.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.online.clickup.common.enums.DependencyType;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request body for creating a task dependency")
public class TaskDependencyRequestDto {

    @NotNull(message = "Task ID is required")
    @Schema(description = "ID of the main task", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID taskId;

    @NotNull(message = "Dependency Task ID is required")
    @Schema(description = "ID of the task that must be completed first", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID dependencyTaskId;

    @NotNull(message = "Dependency type is required")
    @Schema(description = "Dependency type: BLOCKING or WAITING_ON", example = "BLOCKING")
    private DependencyType dependencyType;
}
