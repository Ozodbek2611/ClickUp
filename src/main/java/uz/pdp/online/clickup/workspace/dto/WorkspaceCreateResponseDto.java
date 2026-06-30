package uz.pdp.online.clickup.workspace.dto;

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
@Schema(description = "Workspace details returned after creation")
public class WorkspaceCreateResponseDto {
    @Schema(description = "Unique identifier of the workspace", example = "1")
    private Long id;

    @Schema(description = "Name of the workspace", example = "My Company")
    private String name;

    @Schema(description = "Hex color code", example = "#2C3E50")
    private String color;

    @Schema(description = "First letter of workspace name used as avatar fallback", example = "M")
    private String initialLetter;

    @Schema(description = "ID of the workspace owner", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID ownerId;

    @Schema(description = "ID of the avatar attachment", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID avatarId;

    @Schema(description = "Timestamp when the workspace was created")
    private Timestamp createdAt;
}
