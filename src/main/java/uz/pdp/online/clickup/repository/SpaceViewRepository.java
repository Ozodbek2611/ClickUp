package uz.pdp.online.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.online.clickup.entity.SpaceView;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpaceViewRepository extends JpaRepository<SpaceView, UUID> {

    List<SpaceView> findAllBySpaceId(UUID spaceId);

    Optional<SpaceView> findBySpaceIdAndViewId(UUID spaceId, UUID viewId);

    boolean existsBySpaceIdAndViewId(UUID spaceId, UUID viewId);
}
