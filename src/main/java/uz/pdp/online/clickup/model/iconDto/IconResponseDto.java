package uz.pdp.online.clickup.model.iconDto;

import lombok.Data;

import java.util.UUID;

@Data
public class IconResponseDto {

    private UUID id;

    private UUID attachmentId;

    private String color;

    private String initialLetter;
}
