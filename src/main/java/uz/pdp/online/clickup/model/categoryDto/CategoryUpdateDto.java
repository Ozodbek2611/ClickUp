package uz.pdp.online.clickup.model.categoryDto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Request body for updating a category")
public class CategoryUpdateDto {

    @NotBlank(message = "Category name cannot be blank")
    @Schema(description = "New name for the category", example = "In Progress")
    private String name;

    @Schema(description = "New hex color code", example = "#3498DB")
    private String color;
}
