package uz.pdp.online.clickup.model;

import lombok.Data;
import uz.pdp.online.clickup.entity.enums.TypeOfAction;

import java.util.UUID;

@Data
public class MemberDto {
    private UUID userId;
    private UUID roleId;
    private TypeOfAction typeOfAction;
}
