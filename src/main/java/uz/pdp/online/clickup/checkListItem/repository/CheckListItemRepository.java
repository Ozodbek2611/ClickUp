package uz.pdp.online.clickup.checkListItem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.online.clickup.checkListItem.entity.CheckListItem;

import java.util.List;
import java.util.UUID;

@Repository
public interface CheckListItemRepository extends JpaRepository<CheckListItem, UUID> {

    List<CheckListItem> findAllByCheckListId(UUID checkListId);
}
