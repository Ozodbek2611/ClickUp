package uz.pdp.online.clickup.model.spaceDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class SpaceEditRequestDto {

    @NotNull(message = "Space name cannot be empty")
    private String name;

    @NotNull(message = "Color cannot be empty")
    private String color;

    private UUID avatarId;
}
