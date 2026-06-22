package uz.pdp.online.clickup.model.workspaceDto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Request body for editing a workspace")
public class WorkspaceEditRequestDto {

    @NotBlank(message = "Workspace name cannot be empty")
    @Schema(description = "New name for the workspace", example = "Updated Company Name")
    private String name;

    @NotBlank(message = "Color cannot be empty")
    @Schema(description = "New hex color code", example = "#E67E22")
    private String color;

    @Schema(description = "New avatar attachment ID (optional)", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID avatarId;
}
