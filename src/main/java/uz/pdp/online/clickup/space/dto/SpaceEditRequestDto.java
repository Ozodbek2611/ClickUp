package uz.pdp.online.clickup.space.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Request body for editing a space")
public class SpaceEditRequestDto {

    @NotNull(message = "Space name cannot be empty")
    @Schema(description = "New name for the space", example = "Design")
    private String name;

    @NotNull(message = "Color cannot be empty")
    @Schema(description = "New hex color code", example = "#F39C12")
    private String color;

    @Schema(description = "New avatar attachment ID (optional)", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID avatarId;
}
