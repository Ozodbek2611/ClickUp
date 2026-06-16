package uz.pdp.online.clickup.model.spaceDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import uz.pdp.online.clickup.entity.enums.TypeOfAction;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class SpaceMemberResponseDto {

    private UUID memberId;

    private String memberEmail;

    private TypeOfAction typeOfAction;
}
