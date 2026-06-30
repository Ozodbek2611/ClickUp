package uz.pdp.online.clickup.workspace.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.online.clickup.user.entity.User;
import uz.pdp.online.clickup.common.persistence.BaseUuidEntity;

import java.sql.Timestamp;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkspaceUser extends BaseUuidEntity {

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

    @Column(unique = true)
    private UUID inviteToken;

    private Timestamp inviteExpiresAt;

    @PrePersist
    public void prePersist() {
        this.dateInvited = new Timestamp(System.currentTimeMillis());
        if (this.dateJoined == null) {
            this.inviteToken = UUID.randomUUID();
            this.inviteExpiresAt = new Timestamp(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000);
        }
    }

    public WorkspaceUser(Workspace workspace, User user, WorkspaceRole workspaceRole) {
        this.workspace = workspace;
        this.user = user;
        this.workspaceRole = workspaceRole;
    }

    public boolean isPending() {
        return dateJoined == null;
    }

    public boolean isInviteExpired() {
        return inviteExpiresAt != null && inviteExpiresAt.before(new Timestamp(System.currentTimeMillis()));
    }
}