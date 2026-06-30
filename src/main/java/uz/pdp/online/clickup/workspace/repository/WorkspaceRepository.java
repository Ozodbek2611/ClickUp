package uz.pdp.online.clickup.workspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.online.clickup.workspace.entity.Workspace;

import java.util.UUID;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {

    boolean existsByOwnerIdAndName(UUID ownerId, String name);
}
