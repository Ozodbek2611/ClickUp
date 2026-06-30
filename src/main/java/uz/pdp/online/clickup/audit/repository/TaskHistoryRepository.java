package uz.pdp.online.clickup.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.online.clickup.audit.entity.TaskHistory;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskHistoryRepository extends JpaRepository<TaskHistory, UUID> {

    List<TaskHistory> findAllByTaskIdOrderByCreatedAtDesc(UUID taskId);
}
