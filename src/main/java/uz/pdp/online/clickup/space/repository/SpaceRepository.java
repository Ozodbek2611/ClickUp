package uz.pdp.online.clickup.space.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.online.clickup.space.entity.Space;

import java.util.UUID;

@Repository
public interface SpaceRepository extends JpaRepository<Space, UUID> {
}
