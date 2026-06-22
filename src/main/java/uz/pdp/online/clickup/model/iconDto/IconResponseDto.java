package uz.pdp.online.clickup.model.iconDto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Icon details returned from the API")
public class IconResponseDto {

    @Schema(description = "Unique identifier of the icon", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "ID of the image attachment used as icon", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID attachmentId;

    @Schema(description = "Hex background color", example = "#8E44AD")
    private String color;

    @Schema(description = "Letter shown when no image is set", example = "A")
    private String initialLetter;
}
