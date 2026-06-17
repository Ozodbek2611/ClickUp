package uz.pdp.online.clickup.model.spaceViewDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class SpaceViewRequestDto {

    @NotNull(message = "Space ID is required")
    private UUID spaceId;

    @NotNull(message = "View ID is required")
    private UUID viewId;
}
