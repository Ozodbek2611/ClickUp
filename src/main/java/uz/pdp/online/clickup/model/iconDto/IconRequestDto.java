package uz.pdp.online.clickup.model.iconDto;

import lombok.Data;
import java.util.UUID;

@Data
public class IconRequestDto {

    private UUID attachmentId;

    private String color;

    private String initialLetter;
}
