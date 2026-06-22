package uz.pdp.online.clickup.model.projectDto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Request body for updating a project")
public class ProjectUpdateDto {

    @NotBlank(message = "Project name cannot be blank")
    @Schema(description = "New name for the project", example = "Frontend App")
    private String name;

    @Schema(description = "New hex color code", example = "#E74C3C")
    private String color;
}
