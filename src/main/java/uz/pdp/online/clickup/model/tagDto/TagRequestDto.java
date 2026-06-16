package uz.pdp.online.clickup.model.tagDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagRequestDto {

    @NotBlank(message = "Tag name cannot be blank")
    private String name;

    @NotBlank(message = "Tag color cannot be blank")
    private String color;

    @NotNull(message = "Workspace ID is required")
    private Long workspaceId;
}
