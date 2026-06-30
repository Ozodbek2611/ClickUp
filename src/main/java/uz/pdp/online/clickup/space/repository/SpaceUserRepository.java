package uz.pdp.online.clickup.space.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.online.clickup.space.entity.SpaceUser;

import java.util.UUID;

@Repository
public interface SpaceUserRepository extends JpaRepository<SpaceUser, UUID> {

    @Query("SELECT COUNT(su) > 0 FROM SpaceUser su WHERE su.space.id = :spaceId AND su.member.id = :userId")
    boolean existsBySpaceIdAndUserId(UUID spaceId, UUID userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM SpaceUser su WHERE su.space.id = :spaceId AND su.member.id = :userId")
    void deleteBySpaceIdAndUserId(UUID spaceId, UUID userId);

    void deleteBySpaceId(UUID id);
}
