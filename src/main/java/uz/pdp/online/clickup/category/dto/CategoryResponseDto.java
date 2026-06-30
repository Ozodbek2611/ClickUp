package uz.pdp.online.clickup.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Schema(description = "Category details returned from the API")
public class CategoryResponseDto {

    @Schema(description = "Unique identifier of the category", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "Name of the category", example = "Sprint Backlog")
    private String name;

    @Schema(description = "Hex color code", example = "#FF5733")
    private String color;

    @Schema(description = "Access level: PRIVATE or PUBLIC", example = "PUBLIC")
    private String accessType;

    @Schema(description = "Whether the category is archived", example = "false")
    private Boolean archived;

    @Schema(description = "ID of the parent project", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID projectId;

    @Schema(description = "Timestamp when the category was created")
    private Timestamp createdAt;

    @Schema(description = "ID of the user who created the category", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID createdBy;
}
