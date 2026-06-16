package uz.pdp.online.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.clickup.entity.Priority;

import java.util.UUID;

public interface PriorityRepository extends JpaRepository<Priority, UUID> {
}
