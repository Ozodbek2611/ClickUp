package uz.pdp.online.clickup.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.online.clickup.common.persistence.BaseUuidEntity;
import uz.pdp.online.clickup.common.enums.DependencyType;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskDependency extends BaseUuidEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dependency_task_id")
    private Task dependencyTask;

    @Enumerated(EnumType.STRING)
    private DependencyType dependencyType;
}
