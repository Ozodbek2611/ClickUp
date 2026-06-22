package uz.pdp.online.clickup.model.tagDto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Tag details returned from the API")
public class TagResponseDto {

    @Schema(description = "Unique identifier of the tag", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "Name of the tag", example = "bug")
    private String name;

    @Schema(description = "Hex color code of the tag", example = "#E74C3C")
    private String color;

    @Schema(description = "ID of the parent workspace", example = 1)
    private Long workspaceId;

    @Schema(description = "Timestamp when the tag was created")
    private Timestamp createdAt;

    @Schema(description = "Timestamp when the tag was last updated")
    private Timestamp updatedAt;
}
