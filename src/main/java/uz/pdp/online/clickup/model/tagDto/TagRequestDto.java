package uz.pdp.online.clickup.model.tagDto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request body for creating a tag")
public class TagRequestDto {

    @NotBlank(message = "Tag name cannot be blank")
    @Schema(description = "Name of the tag", example = "bug")
    private String name;

    @NotBlank(message = "Tag color cannot be blank")
    @Schema(description = "Hex color code for the tag", example = "#E74C3C")
    private String color;

    @NotNull(message = "Workspace ID is required")
    @Schema(description = "ID of the workspace this tag belongs to", example = "1")
    private Long workspaceId;
}
