package uz.pdp.online.clickup.model.projectUserDto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.pdp.online.clickup.entity.enums.TaskPermission;

import java.util.UUID;

@Data
@Schema(description = "Request body for assigning a user to a project")
public class ProjectUserRequestDto {

    @NotNull(message = "Project ID is required")
    @Schema(description = "ID of the project", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID projectId;

    @NotNull(message = "User ID is required")
    @Schema(description = "ID of the user to assign", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID userId;

    @NotNull(message = "Task Permission is required")
    @Schema(description = "Permission level: CREATE, EDIT, or VIEW", example = "CREATE")
    private TaskPermission taskPermission;
}
