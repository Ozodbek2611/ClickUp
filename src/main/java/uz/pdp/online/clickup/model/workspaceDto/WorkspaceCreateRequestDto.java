package uz.pdp.online.clickup.model.workspaceDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class WorkspaceCreateRequestDto {

    @NotBlank(message = "Workspace name cannot be empty")
    @Size(max = 50, message = "Workspace name cannot exceed 50 characters")
    private String name;

    @NotBlank(message = "Color cannot be empty")
    private String color;

    private UUID avatarId;
}
