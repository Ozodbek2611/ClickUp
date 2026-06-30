package uz.pdp.online.clickup.status.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.online.clickup.status.entity.Status;

import java.util.List;
import java.util.UUID;

@Repository
public interface StatusRepository extends JpaRepository<Status, UUID> {

    List<Status> findAllBySpaceId(UUID spaceId);
}
