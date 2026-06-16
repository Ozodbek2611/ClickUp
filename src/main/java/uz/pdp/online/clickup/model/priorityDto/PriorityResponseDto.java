package uz.pdp.online.clickup.model.priorityDto;

import lombok.Data;
import java.util.UUID;

@Data
public class PriorityResponseDto {

    private UUID id;

    private String name;

    private UUID iconId;
}
