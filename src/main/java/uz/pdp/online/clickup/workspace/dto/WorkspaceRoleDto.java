package uz.pdp.online.clickup.workspace.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import uz.pdp.online.clickup.common.enums.TypeOfAction;
import uz.pdp.online.clickup.common.enums.WorkspacePermissionName;

import java.util.UUID;

@Data
@Schema(description = "Request body for updating role permissions")
public class WorkspaceRoleDto {

    @Schema(description = "ID of the workspace role", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "Permission name to add or remove", example = "MANAGE_MEMBERS")
    private WorkspacePermissionName workspacePermissionName;

    @Schema(description = "Action to perform: ADD or REMOVE", example = "ADD")
    private TypeOfAction typeOfAction;
}
