package uz.pdp.online.clickup.model.attachmentDto;

import lombok.Data;
import java.util.UUID;

@Data
public class AttachmentResponseDto {

    private UUID id;

    private String name;

    private String originalName;

    private Long size;

    private String contentType;

    private String downloadUrl;
}
