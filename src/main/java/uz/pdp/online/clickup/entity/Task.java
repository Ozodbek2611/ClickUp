package uz.pdp.online.clickup.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import uz.pdp.online.clickup.entity.template.AbsUUIDEntity;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Task extends AbsUUIDEntity {
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "priority_id")
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "parent_task_id")
    private Task parentTask;

    @CreatedDate
    private LocalDateTime startedDate;

    private LocalDateTime startTimeHas;

    private LocalDateTime dueDate;

    private LocalDateTime dueTimeHas;

    private Long estimateTime;

    private LocalDateTime activatedDate;
}
