package uz.pdp.online.clickup.category.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.pdp.online.clickup.project.entity.Project;
import uz.pdp.online.clickup.common.persistence.BaseUuidEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Category extends BaseUuidEntity {

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    private String accessType;

    private Boolean archived;

    private String color;
}
