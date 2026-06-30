package uz.pdp.online.clickup.status.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.online.clickup.common.enums.Type;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Status details returned from the API")
public class StatusResponseDto {

    @Schema(description = "Unique identifier of the status", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "Name of the status", example = "In Progress")
    private String name;

    @Schema(description = "ID of the parent space", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID spaceId;

    @Schema(description = "ID of the parent project", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID projectId;

    @Schema(description = "ID of the parent category", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID categoryId;

    @Schema(description = "Hex color code of the status", example = "#F39C12")
    private String color;

    @Schema(description = "Status type enum value", example = "IN_PROGRESS")
    private Type type;

    @Schema(description = "Timestamp when the status was created")
    private Timestamp createdAt;

    @Schema(description = "Timestamp when the status was last updated")
    private Timestamp updatedAt;
}
