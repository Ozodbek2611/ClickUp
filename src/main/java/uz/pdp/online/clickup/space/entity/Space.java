package uz.pdp.online.clickup.space.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.online.clickup.attachment.entity.Attachment;
import uz.pdp.online.clickup.workspace.entity.Workspace;
import uz.pdp.online.clickup.common.persistence.BaseUuidEntity;
import uz.pdp.online.clickup.icon.entity.Icon;
import uz.pdp.online.clickup.user.entity.User;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Space extends BaseUuidEntity {

    private String name;

    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @Column(nullable = false)
    private String initialLetter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "icon_id")
    private Icon icon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avatar_id")
    private Attachment avatar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    private String accessType;

    @PrePersist
    @PreUpdate
    public void setInitialLetter() {
        this.initialLetter = name.substring(0, 1).toUpperCase();
    }
}
