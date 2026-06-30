package uz.pdp.online.clickup.priority.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.pdp.online.clickup.common.persistence.BaseUuidEntity;
import uz.pdp.online.clickup.icon.entity.Icon;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Priority extends BaseUuidEntity {

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "icon_id")
    private Icon icon;
}
