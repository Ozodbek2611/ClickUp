package uz.pdp.online.clickup.model.workspaceDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.pdp.online.clickup.entity.enums.TypeOfAction;

import java.util.UUID;

@Data
public class MemberRequestDto {

    @NotNull(message = "User ID is required")
    private UUID userId;

    private UUID roleId;

    @NotNull(message = "Action type is required")
    private TypeOfAction typeOfAction;
}
