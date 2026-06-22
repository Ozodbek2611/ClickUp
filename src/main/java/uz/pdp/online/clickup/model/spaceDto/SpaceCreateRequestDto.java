package uz.pdp.online.clickup.model.spaceDto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Request body for creating a new space")
public class SpaceCreateRequestDto {

    @NotBlank(message = "Space name cannot be empty")
    @Schema(description = "Name of the space", example = "Development")
    private String name;

    @NotBlank(message = "Color cannot be empty")
    @Schema(description = "Hex color code for the space", example = "#1ABC9C")
    private String color;

    @Schema(description = "ID of the avatar image attachment (optional)", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID avatarId;

    @NotNull(message = "Workspace ID is required")
    @Schema(description = "ID of the parent workspace", example = 1)
    private Long workspaceId;

    @Schema(description = "Access level: PRIVATE or PUBLIC", example = "PUBLIC")
    private String accessType;
}
