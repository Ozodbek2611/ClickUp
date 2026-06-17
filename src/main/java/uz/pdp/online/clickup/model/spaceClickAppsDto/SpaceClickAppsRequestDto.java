package uz.pdp.online.clickup.model.spaceClickAppsDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class SpaceClickAppsRequestDto {

    @NotNull(message = "Space ID is required")
    private UUID spaceId;

    @NotNull(message = "ClickApps ID is required")
    private UUID clickAppsId;
}
