package uz.pdp.online.clickup.space.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "ClickApp assignment details for a space")
public class SpaceClickAppsResponseDto {

    @Schema(description = "Unique identifier of the assignment", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "ID of the space", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID spaceId;

    @Schema(description = "ID of the enabled ClickApp", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID clickAppsId;

    @Schema(description = "Name of the ClickApp", example = "Time Tracking")
    private String clickAppsName;
}
