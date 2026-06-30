package uz.pdp.online.clickup.space.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Updated space details")
public class SpaceEditResponseDto {

    @Schema(description = "Unique identifier of the space", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "Updated name of the space", example = "Design")
    private String name;

    @Schema(description = "Updated hex color code", example = "#F39C12")
    private String color;

    @Schema(description = "ID of the updated avatar attachment", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID avatarId;
}
