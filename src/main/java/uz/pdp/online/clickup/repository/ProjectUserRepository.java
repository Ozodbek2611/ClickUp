package uz.pdp.online.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.online.clickup.entity.relation.ProjectUser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUser, UUID> {

    List<ProjectUser> findAllByProjectId(UUID projectId);

    Optional<ProjectUser> findByProjectIdAndUserId(UUID projectId, UUID userId);

    void deleteByProjectIdAndUserId(UUID projectId, UUID userId);
}
