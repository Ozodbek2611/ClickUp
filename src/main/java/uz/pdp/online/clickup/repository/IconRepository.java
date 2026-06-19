package uz.pdp.online.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.clickup.entity.domain.Icon;

import java.util.UUID;

public interface IconRepository extends JpaRepository<Icon, UUID> {
}
