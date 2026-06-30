package uz.pdp.online.clickup.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
@Schema(description = "Request body for creating a new category (list)")
public class CategoryRequestDto {

    @NotBlank(message = "Category name cannot be blank")
    @Schema(description = "Name of the category", example = "Sprint Backlog")
    private String name;

    @Schema(description = "Hex color code for the category", example = "#FF5733")
    private String color;

    @Schema(description = "Access level: PRIVATE or PUBLIC", example = "PUBLIC")
    private String accessType;

    @NotNull(message = "Project ID cannot be null")
    @Schema(description = "ID of the parent project", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID projectId;
}
