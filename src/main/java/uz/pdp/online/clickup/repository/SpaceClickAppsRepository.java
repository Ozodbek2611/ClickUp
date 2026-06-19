package uz.pdp.online.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.online.clickup.entity.relation.SpaceClickApps;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpaceClickAppsRepository extends JpaRepository<SpaceClickApps, UUID> {

    List<SpaceClickApps> findAllBySpaceId(UUID spaceId);

    Optional<SpaceClickApps> findBySpaceIdAndClickAppsId(UUID spaceId, UUID clickAppsId);

    boolean existsBySpaceIdAndClickAppsId(UUID spaceId, UUID clickAppsId);
}
