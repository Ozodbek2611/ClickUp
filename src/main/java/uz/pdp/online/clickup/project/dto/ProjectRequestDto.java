package uz.pdp.online.clickup.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Request body for creating a new project")
public class ProjectRequestDto {

    @NotBlank(message = "Project name cannot be blank")
    @Schema(description = "Name of the project", example = "Backend API")
    private String name;

    @Schema(description = "Hex color code for the project", example = "#2ECC71")
    private String color;

    @NotNull(message = "Space ID cannot be null")
    @Schema(description = "ID of the parent space", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID spaceId;
}
