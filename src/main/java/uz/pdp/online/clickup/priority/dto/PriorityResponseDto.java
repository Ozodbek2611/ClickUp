package uz.pdp.online.clickup.priority.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import java.util.UUID;

@Data
@Schema(description = "Priority details returned from the API")
public class PriorityResponseDto {

    @Schema(description = "Unique identifier of the priority", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "Name of the priority level", example = "Urgent")
    private String name;

    @Schema(description = "ID of the associated icon", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID iconId;
}
