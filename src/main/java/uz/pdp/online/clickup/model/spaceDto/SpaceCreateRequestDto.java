package uz.pdp.online.clickup.model.spaceDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class SpaceCreateRequestDto {

    @NotBlank(message = "Space name cannot be empty")
    private String name;

    @NotBlank(message = "Color cannot be empty")
    private String color;

    private UUID avatarId;

    @NotNull(message = "Workspace ID is required")
    private Long workspaceId;

    private String accessType;
}
