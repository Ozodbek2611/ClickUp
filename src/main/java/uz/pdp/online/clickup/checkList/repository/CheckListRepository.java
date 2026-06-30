package uz.pdp.online.clickup.checkList.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.online.clickup.checkList.entity.CheckList;

import java.util.List;
import java.util.UUID;

@Repository
public interface CheckListRepository extends JpaRepository<CheckList, UUID> {

    List<CheckList> findAllByTaskId(UUID taskId);
}
