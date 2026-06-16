package uz.pdp.online.clickup.model.workspaceDto;

import lombok.Data;
import uz.pdp.online.clickup.entity.enums.TypeOfAction;
import uz.pdp.online.clickup.entity.enums.WorkspacePermissionName;

import java.util.UUID;

@Data
public class WorkspaceRoleDto {

    private UUID id;

    private WorkspacePermissionName workspacePermissionName;

    private TypeOfAction typeOfAction;
}
