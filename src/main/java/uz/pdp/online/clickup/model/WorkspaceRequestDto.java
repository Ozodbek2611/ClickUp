package uz.pdp.online.clickup.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class WorkspaceRequestDto {
    @NotNull
    private String name;

    @NotNull
    private String color;

    private UUID avatarId;
}
