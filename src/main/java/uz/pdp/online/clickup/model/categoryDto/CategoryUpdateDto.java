package uz.pdp.online.clickup.model.categoryDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryUpdateDto {

    @NotBlank(message = "Category name cannot be blank")
    private String name;

    private String color;
}
