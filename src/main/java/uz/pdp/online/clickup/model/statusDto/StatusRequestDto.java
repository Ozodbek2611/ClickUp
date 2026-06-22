package uz.pdp.online.clickup.model.statusDto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.online.clickup.entity.enums.Type;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request body for creating or updating a status")
public class StatusRequestDto {

    @NotBlank(message = "Status name cannot be blank")
    @Schema(description = "Name of the status", example = "In Progress")
    private String name;

    @Schema(description = "Hex color code for the status", example = "#F39C12")
    private String color;

    @NotNull(message = "Status type is required")
    @Schema(description = "Status type: OPEN, IN_PROGRESS, CLOSED, or CUSTOM", example = "IN_PROGRESS")
    private Type type;

    @Schema(description = "ID of the space this status belongs to (optional)", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID spaceId;

    @Schema(description = "ID of the project this status belongs to (optional)", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID projectId;

    @Schema(description = "ID of the category this status belongs to (optional)", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID categoryId;
}
