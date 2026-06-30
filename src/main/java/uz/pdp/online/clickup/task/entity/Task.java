package uz.pdp.online.clickup.task.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import uz.pdp.online.clickup.category.entity.Category;
import uz.pdp.online.clickup.common.persistence.BaseUuidEntity;
import uz.pdp.online.clickup.priority.entity.Priority;
import uz.pdp.online.clickup.status.entity.Status;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Task extends BaseUuidEntity {

    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priority_id")
    private Priority priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_task_id")
    private Task parentTask;

    @CreationTimestamp
    private Timestamp startedDate;

    private Timestamp startTimeHas;

    private Timestamp dueDate;

    private Timestamp dueTimeHas;

    private Long estimateTime;

    private Timestamp activatedDate;
}
