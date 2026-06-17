package uz.pdp.online.clickup.model.taskDependencyDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.online.clickup.entity.enums.DependencyType;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDependencyRequestDto {

    @NotNull(message = "Task ID is required")
    private UUID taskId;

    @NotNull(message = "Dependency Task ID is required")
    private UUID dependencyTaskId;

    @NotNull(message = "Dependency type is required")
    private DependencyType dependencyType;
}
