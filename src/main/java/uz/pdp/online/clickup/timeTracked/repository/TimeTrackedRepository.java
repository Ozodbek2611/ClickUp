package uz.pdp.online.clickup.timeTracked.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.clickup.timeTracked.entity.TimeTracked;

import java.util.List;
import java.util.UUID;

public interface TimeTrackedRepository extends JpaRepository<TimeTracked, UUID> {

    List<TimeTracked> findAllByTaskId(UUID taskId);
}
