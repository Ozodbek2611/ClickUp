package uz.pdp.online.clickup.model.checkListDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckListRequestDto {

    @NotBlank(message = "Checklist name cannot be blank")
    private String name;

    @NotNull(message = "Task ID is required")
    private UUID taskId;
}
