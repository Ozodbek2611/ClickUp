package uz.pdp.online.clickup.space.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Space view assignment details")
public class SpaceViewResponseDto {

    @Schema(description = "Unique identifier of the assignment", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "ID of the space", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID spaceId;

    @Schema(description = "ID of the view type", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID viewId;

    @Schema(description = "Name of the view (e.g. Board, List, Gantt)", example = "Board")
    private String viewName;
}
