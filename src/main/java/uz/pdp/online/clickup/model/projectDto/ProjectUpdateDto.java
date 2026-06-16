package uz.pdp.online.clickup.model.projectDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProjectUpdateDto {

    @NotBlank(message = "Project name cannot be blank")
    private String name;

    private String color;
}
