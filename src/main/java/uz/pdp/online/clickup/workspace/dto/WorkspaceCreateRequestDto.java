package uz.pdp.online.clickup.workspace.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Request body for creating a new workspace")
public class WorkspaceCreateRequestDto {

    @NotBlank(message = "Workspace name cannot be empty")
    @Size(max = 50, message = "Workspace name cannot exceed 50 characters")
    @Schema(description = "Name of the workspace (max 50 characters)", example = "My Company")
    private String name;

    @NotBlank(message = "Color cannot be empty")
    @Schema(description = "Hex color code for the workspace", example = "#2C3E50")
    private String color;

    @Schema(description = "ID of the avatar image attachment (optional)", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID avatarId;
}
