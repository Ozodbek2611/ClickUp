package uz.pdp.online.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.online.clickup.entity.relation.WorkspacePermission;
import uz.pdp.online.clickup.entity.domain.WorkspaceRole;
import uz.pdp.online.clickup.entity.enums.WorkspacePermissionName;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WorkspacePermissionRepository extends JpaRepository<WorkspacePermission, UUID> {

    Optional<WorkspacePermission> findByWorkspaceRoleIdAndPermission(UUID workspaceRole_id, WorkspacePermissionName permission);

    void deleteAllByWorkspaceRoleIn(List<WorkspaceRole> roles);
}
