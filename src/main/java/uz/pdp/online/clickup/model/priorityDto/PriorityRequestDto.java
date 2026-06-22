package uz.pdp.online.clickup.model.priorityDto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.UUID;

@Data
@Schema(description = "Request body for creating or updating a priority")
public class PriorityRequestDto {

    @NotBlank(message = "Priority name cannot be blank")
    @Schema(description = "Name of the priority level", example = "Urgent")
    private String name;

    @Schema(description = "ID of the icon associated with this priority", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID iconId;
}
