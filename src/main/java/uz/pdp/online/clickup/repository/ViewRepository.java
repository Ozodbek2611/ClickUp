package uz.pdp.online.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.clickup.entity.domain.View;

import java.util.UUID;

public interface ViewRepository extends JpaRepository<View, UUID> {
}
