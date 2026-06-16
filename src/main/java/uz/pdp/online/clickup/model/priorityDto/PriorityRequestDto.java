package uz.pdp.online.clickup.model.priorityDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.UUID;

@Data
public class PriorityRequestDto {

    @NotBlank(message = "Priority name cannot be blank")
    private String name;

    private UUID iconId;
}
