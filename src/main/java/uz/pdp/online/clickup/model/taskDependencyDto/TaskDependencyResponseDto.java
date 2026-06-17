package uz.pdp.online.clickup.model.taskDependencyDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.online.clickup.entity.enums.DependencyType;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDependencyResponseDto {

    private UUID id;

    private UUID taskId;

    private UUID dependencyTaskId;

    private DependencyType dependencyType;

    private Timestamp createdAt;
}
