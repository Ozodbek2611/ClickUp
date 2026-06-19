package uz.pdp.online.clickup.entity.relation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.online.clickup.entity.domain.Project;
import uz.pdp.online.clickup.entity.domain.User;
import uz.pdp.online.clickup.entity.template.AbsUUIDEntity;
import uz.pdp.online.clickup.entity.enums.TaskPermission;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjectUser extends AbsUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private TaskPermission taskPermission;
}
