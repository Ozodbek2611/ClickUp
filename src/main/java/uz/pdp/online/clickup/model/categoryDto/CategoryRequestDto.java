package uz.pdp.online.clickup.model.categoryDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
public class CategoryRequestDto {

    @NotBlank(message = "Category name cannot be blank")
    private String name;

    private String color;

    private String accessType;

    @NotNull(message = "Project ID cannot be null")
    private UUID projectId;
}
