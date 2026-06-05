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
public class Space extends AbsUUIDEntity {
    private String name;

    private String color;

    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    private String initialLetter;

    @ManyToOne
    @JoinColumn(name = "icon_id")
    private Icon icon;

    @ManyToOne
    @JoinColumn(name = "avatar_id")
    private Attachment avatar;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    private String accessType;
}
