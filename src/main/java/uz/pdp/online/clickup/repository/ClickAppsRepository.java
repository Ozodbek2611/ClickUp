package uz.pdp.online.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.clickup.entity.ClickApps;

import java.util.UUID;

public interface ClickAppsRepository extends JpaRepository<ClickApps, UUID> {
}
