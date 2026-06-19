package uz.pdp.online.clickup.entity.relation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.online.clickup.entity.domain.User;
import uz.pdp.online.clickup.entity.domain.Workspace;
import uz.pdp.online.clickup.entity.domain.WorkspaceRole;
import uz.pdp.online.clickup.entity.template.AbsUUIDEntity;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkspaceUser extends AbsUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workspace_role_id")
    private WorkspaceRole workspaceRole;

    @Column(nullable = false)
    private Timestamp dateInvited;

    private Timestamp dateJoined;

    @PrePersist
    public void prePersist() {
        this.dateInvited = new Timestamp(System.currentTimeMillis());
    }

    public WorkspaceUser(Workspace workspace, User user, WorkspaceRole workspaceRole) {
        this.workspace = workspace;
        this.user = user;
        this.workspaceRole = workspaceRole;
    }
}
