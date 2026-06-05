package uz.pdp.online.clickup.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.online.clickup.entity.template.AbsUUIDEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckListItem extends AbsUUIDEntity {
    @ManyToOne
    @JoinColumn(name = "check_list_id")
    private CheckList checkList;

    private String resolved;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;
}
