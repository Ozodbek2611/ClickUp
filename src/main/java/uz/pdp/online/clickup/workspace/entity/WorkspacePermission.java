package uz.pdp.online.clickup.workspace.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.online.clickup.common.enums.WorkspacePermissionName;
import uz.pdp.online.clickup.common.persistence.BaseUuidEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkspacePermission extends BaseUuidEntity {

    @ManyToOne(fetch = FetchType.LAZY,  optional = false)
    @JoinColumn(name = "workspace_role_id")
    private WorkspaceRole workspaceRole;

    @Enumerated(EnumType.STRING)
    private WorkspacePermissionName permission;
}
