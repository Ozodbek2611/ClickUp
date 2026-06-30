package uz.pdp.online.clickup.workspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.online.clickup.workspace.entity.WorkspaceUser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WorkspaceUserRepository extends JpaRepository<WorkspaceUser, UUID> {

    Optional<WorkspaceUser> findByWorkspaceIdAndUserId(Long workspace_id, UUID user_id);

    @Transactional
    @Modifying
    void deleteByWorkspaceIdAndUserId(Long workspace_id, UUID user_id);

    @Query("SELECT wu FROM WorkspaceUser wu JOIN FETCH wu.user JOIN FETCH wu.workspaceRole WHERE wu.workspace.id = :id")
    List<WorkspaceUser> findAllByWorkspaceId(@Param("id") Long id);

    @Query("SELECT wu FROM WorkspaceUser wu JOIN FETCH wu.workspace w JOIN FETCH w.owner WHERE wu.user.id = :userId")
    List<WorkspaceUser> findAllByUserId(@Param("userId") UUID userId);

    void deleteAllByWorkspaceId(Long id);
}
