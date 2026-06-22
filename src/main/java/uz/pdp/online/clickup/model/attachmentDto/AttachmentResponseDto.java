package uz.pdp.online.clickup.model.attachmentDto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import java.util.UUID;

@Data
@Schema(description = "Uploaded file metadata")
public class AttachmentResponseDto {

    @Schema(description = "Unique identifier of the attachment", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "Stored file name on server", example = "abc123.png")
    private String name;

    @Schema(description = "Original file name uploaded by user", example = "profile-photo.png")
    private String originalName;

    @Schema(description = "File size in bytes", example = "204800")
    private Long size;

    @Schema(description = "MIME type of the file", example = "image/png")
    private String contentType;

    @Schema(description = "URL to download the file", example = "/api/attachment/download/3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private String downloadUrl;
}
