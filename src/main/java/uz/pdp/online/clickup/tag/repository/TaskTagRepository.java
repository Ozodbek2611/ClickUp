package uz.pdp.online.clickup.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.online.clickup.tag.entity.TaskTag;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskTagRepository extends JpaRepository<TaskTag, UUID> {

    Optional<TaskTag> findByTaskIdAndTagId(UUID taskId, UUID tagId);

    void deleteByTaskIdAndTagId(UUID taskId, UUID tagId);
}
