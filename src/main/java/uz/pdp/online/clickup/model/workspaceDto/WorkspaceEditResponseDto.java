package uz.pdp.online.clickup.model.workspaceDto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Updated workspace details")
public class WorkspaceEditResponseDto {

    @Schema(description = "Unique identifier of the workspace", example = "1")
    private Long id;

    @Schema(description = "Updated workspace name", example = "Updated Company Name")
    private String name;

    @Schema(description = "Updated hex color code", example = "#E67E22")
    private String color;

    @Schema(description = "First letter of workspace name", example = "U")
    private String initialLetter;

    @Schema(description = "ID of the avatar attachment", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID avatarId;

    @Schema(description = "Timestamp when the workspace was last updated")
    private Timestamp updatedAt;
}
