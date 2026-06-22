package uz.pdp.online.clickup.model.iconDto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import java.util.UUID;

@Data
@Schema(description = "Request body for creating an icon")
public class IconRequestDto {

    @Schema(description = "ID of the uploaded image to use as icon (optional)", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID attachmentId;

    @Schema(description = "Hex background color for the icon", example = "#8E44AD")
    private String color;

    @Schema(description = "Single letter shown when no image is provided", example = "A")
    private String initialLetter;
}
