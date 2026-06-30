package uz.pdp.online.clickup.space.dto;

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
@Schema(description = "Space details returned after creation")
public class SpaceCreateResponseDto {

    @Schema(description = "Unique identifier of the space", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "Name of the space", example = "Development")
    private String name;

    @Schema(description = "Hex color code", example = "#1ABC9C")
    private String color;

    @Schema(description = "Access level: PRIVATE or PUBLIC", example = "PUBLIC")
    private String accessType;

    @Schema(description = "ID of the parent workspace", example = "1")
    private Long workspaceId;

    @Schema(description = "ID of the space owner", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID ownerId;

    @Schema(description = "ID of the avatar attachment", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID avatarId;

    @Schema(description = "Timestamp when the space was created")
    private Timestamp createdAt;
}
