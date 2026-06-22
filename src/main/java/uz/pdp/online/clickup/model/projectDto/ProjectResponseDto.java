package uz.pdp.online.clickup.model.projectDto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Schema(description = "Project details returned from the API")
public class ProjectResponseDto {

    @Schema(description = "Unique identifier of the project", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "Name of the project", example = "Backend API")
    private String name;

    @Schema(description = "Hex color code", example = "#2ECC71")
    private String color;

    @Schema(description = "ID of the parent space", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID spaceId;

    @Schema(description = "Timestamp when the project was created")
    private Timestamp createdAt;

    @Schema(description = "ID of the user who created the project", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID createdBy;
}
