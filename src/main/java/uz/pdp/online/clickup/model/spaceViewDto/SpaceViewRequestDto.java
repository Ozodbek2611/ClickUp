package uz.pdp.online.clickup.model.spaceViewDto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Request body for adding a view to a space")
public class SpaceViewRequestDto {

    @NotNull(message = "Space ID is required")
    @Schema(description = "ID of the space", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID spaceId;

    @NotNull(message = "View ID is required")
    @Schema(description = "ID of the view type to add", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID viewId;
}
