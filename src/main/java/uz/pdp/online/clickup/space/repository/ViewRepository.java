package uz.pdp.online.clickup.space.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.clickup.space.entity.View;

import java.util.UUID;

public interface ViewRepository extends JpaRepository<View, UUID> {
}
