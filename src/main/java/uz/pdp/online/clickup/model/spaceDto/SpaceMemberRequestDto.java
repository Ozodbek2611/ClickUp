package uz.pdp.online.clickup.model.spaceDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.pdp.online.clickup.entity.enums.TypeOfAction;

import java.util.UUID;

@Data
public class SpaceMemberRequestDto {

    @NotNull
    private UUID memberId;

    @NotNull
    private TypeOfAction typeOfAction;
}
