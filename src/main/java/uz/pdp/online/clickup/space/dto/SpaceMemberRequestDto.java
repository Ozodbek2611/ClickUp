package uz.pdp.online.clickup.space.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.pdp.online.clickup.common.enums.TypeOfAction;

import java.util.UUID;

@Data
@Schema(description = "Request body for adding or removing a space member")
public class SpaceMemberRequestDto {

    @NotNull
    @Schema(description = "ID of the user to add or remove", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID memberId;

    @NotNull
    @Schema(description = "Action to perform: ADD or REMOVE", example = "ADD")
    private TypeOfAction typeOfAction;
}
