package uz.pdp.online.clickup.model.workspaceDto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.pdp.online.clickup.entity.enums.TypeOfAction;

import java.util.UUID;

@Data
@Schema(description = "Request body for adding, editing, or removing a workspace member")
public class MemberRequestDto {

    @NotNull(message = "User ID is required")
    @Schema(description = "ID of the user", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID userId;

    @Schema(description = "ID of the role to assign (optional)", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID roleId;

    @NotNull(message = "Action type is required")
    @Schema(description = "Action to perform: ADD, EDIT, or REMOVE", example = "ADD")
    private TypeOfAction typeOfAction;
}
