package uz.pdp.online.clickup.audit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.online.clickup.task.entity.Task;
import uz.pdp.online.clickup.common.enums.TaskHistoryType;
import uz.pdp.online.clickup.common.persistence.BaseUuidEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskHistory extends BaseUuidEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Column(nullable = false)
    private String changeFieldName;

    @Column(columnDefinition = "TEXT")
    private String beforeValue;

    @Column(columnDefinition = "TEXT")
    private String afterValue;

    @Enumerated(EnumType.STRING)
    private TaskHistoryType historyType;
}
