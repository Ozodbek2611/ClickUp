package uz.pdp.online.clickup.model.spaceClickAppsDto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Request body for enabling a ClickApp in a space")
public class SpaceClickAppsRequestDto {

    @NotNull(message = "Space ID is required")
    @Schema(description = "ID of the space", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID spaceId;

    @NotNull(message = "ClickApps ID is required")
    @Schema(description = "ID of the ClickApp to enable", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID clickAppsId;
}
