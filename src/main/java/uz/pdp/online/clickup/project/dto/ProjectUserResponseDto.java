package uz.pdp.online.clickup.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import uz.pdp.online.clickup.common.enums.TaskPermission;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Schema(description = "Project user assignment details")
public class ProjectUserResponseDto {

    @Schema(description = "Unique identifier of the assignment", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "ID of the project", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID projectId;

    @Schema(description = "ID of the assigned user", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID userId;

    @Schema(description = "Email of the assigned user", example = "john@gmail.com")
    private String userEmail;

    @Schema(description = "Permission level granted", example = "CREATE")
    private TaskPermission taskPermission;

    @Schema(description = "Timestamp when the assignment was created")
    private Timestamp createdAt;
}
