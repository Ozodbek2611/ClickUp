package uz.pdp.online.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.clickup.entity.domain.Project;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {

    List<Project> findAllBySpaceId(UUID spaceId);
}
