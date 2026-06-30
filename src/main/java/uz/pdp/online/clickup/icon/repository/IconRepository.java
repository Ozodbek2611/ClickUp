package uz.pdp.online.clickup.icon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.clickup.icon.entity.Icon;

import java.util.UUID;

public interface IconRepository extends JpaRepository<Icon, UUID> {
}
