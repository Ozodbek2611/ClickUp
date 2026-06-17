package uz.pdp.online.clickup.model.projectUserDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.pdp.online.clickup.entity.enums.TaskPermission;

import java.util.UUID;

@Data
public class ProjectUserRequestDto {

    @NotNull(message = "Project ID is required")
    private UUID projectId;

    @NotNull(message = "User ID is required")
    private UUID userId;

    @NotNull(message = "Task Permission is required")
    private TaskPermission taskPermission;
}
