package uz.pdp.online.clickup.model.projectDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ProjectRequestDto {

    @NotBlank(message = "Project name cannot be blank")
    private String name;

    private String color;

    @NotNull(message = "Space ID cannot be null")
    private UUID spaceId;
}
