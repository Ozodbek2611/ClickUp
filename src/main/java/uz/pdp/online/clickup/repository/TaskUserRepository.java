package uz.pdp.online.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.online.clickup.entity.TaskUser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskUserRepository extends JpaRepository<TaskUser, UUID> {

    List<TaskUser> findAllByTaskId(UUID taskId);

    Optional<TaskUser> findByTaskIdAndUserId(UUID taskId, UUID userId);

    void deleteByTaskIdAndUserId(UUID taskId, UUID userId);
}
