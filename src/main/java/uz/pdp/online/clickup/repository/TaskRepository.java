package uz.pdp.online.clickup.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.online.clickup.entity.Task;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends CrudRepository<Task, UUID> {

    List<Task> findAllByCategoryId(UUID categoryId);
}
