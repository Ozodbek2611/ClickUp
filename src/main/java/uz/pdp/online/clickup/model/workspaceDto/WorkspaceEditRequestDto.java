package uz.pdp.online.clickup.model.workspaceDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class WorkspaceEditRequestDto {

    @NotBlank(message = "Workspace name cannot be empty")
    private String name;

    @NotBlank(message = "Color cannot be empty")
    private String color;

    private UUID avatarId;
}
