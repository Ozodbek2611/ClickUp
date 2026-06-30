package uz.pdp.online.clickup.workspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.online.clickup.workspace.entity.WorkspaceRole;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkspaceRoleRepository extends JpaRepository<WorkspaceRole, UUID> {

    List<WorkspaceRole> findByWorkspaceId(Long id);

    void deleteAllByWorkspaceId(Long id);
}
