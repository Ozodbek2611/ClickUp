package uz.pdp.online.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.online.clickup.entity.domain.CheckListItem;

import java.util.List;
import java.util.UUID;

@Repository
public interface CheckListItemRepository extends JpaRepository<CheckListItem, UUID> {

    List<CheckListItem> findAllByCheckListId(UUID checkListId);
}
