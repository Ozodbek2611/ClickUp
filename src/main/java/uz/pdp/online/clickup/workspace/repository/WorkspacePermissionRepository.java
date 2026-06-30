package uz.pdp.online.clickup.workspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.online.clickup.workspace.entity.WorkspacePermission;
import uz.pdp.online.clickup.workspace.entity.WorkspaceRole;
import uz.pdp.online.clickup.common.enums.WorkspacePermissionName;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WorkspacePermissionRepository extends JpaRepository<WorkspacePermission, UUID> {

    Optional<WorkspacePermission> findByWorkspaceRoleIdAndPermission(UUID workspaceRole_id, WorkspacePermissionName permission);

    void deleteAllByWorkspaceRoleIn(List<WorkspaceRole> roles);
}
