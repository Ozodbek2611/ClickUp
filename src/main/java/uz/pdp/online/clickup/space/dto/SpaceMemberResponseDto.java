package uz.pdp.online.clickup.space.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import uz.pdp.online.clickup.common.enums.TypeOfAction;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Space member action result")
public class SpaceMemberResponseDto {

    @Schema(description = "ID of the affected user", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID memberId;

    @Schema(description = "Email of the affected user", example = "john@gmail.com")
    private String memberEmail;

    @Schema(description = "Action that was performed: ADD or REMOVE", example = "ADD")
    private TypeOfAction typeOfAction;
}
