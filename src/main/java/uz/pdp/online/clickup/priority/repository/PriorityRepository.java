package uz.pdp.online.clickup.priority.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.clickup.priority.entity.Priority;

import java.util.UUID;

public interface PriorityRepository extends JpaRepository<Priority, UUID> {
}
