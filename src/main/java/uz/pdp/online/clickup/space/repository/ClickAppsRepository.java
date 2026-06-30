package uz.pdp.online.clickup.space.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.clickup.space.entity.ClickApps;

import java.util.UUID;

public interface ClickAppsRepository extends JpaRepository<ClickApps, UUID> {
}
