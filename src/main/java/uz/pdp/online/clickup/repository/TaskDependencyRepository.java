package uz.pdp.online.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.online.clickup.entity.relation.TaskDependency;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskDependencyRepository extends JpaRepository<TaskDependency, UUID> {

    List<TaskDependency> findAllByTaskId(UUID taskId);

    boolean existsByTaskIdAndDependencyTaskId(UUID taskId, UUID dependencyTaskId);
}
