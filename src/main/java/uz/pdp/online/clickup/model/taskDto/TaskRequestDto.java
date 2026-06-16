package uz.pdp.online.clickup.model.taskDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestDto {

    @NotBlank(message = "Task name cannot be blank")
    private String name;

    private String description;

    @NotNull(message = "Status ID is required")
    private UUID statusId;

    @NotNull(message = "Category ID is required")
    private UUID categoryId;

    private UUID priorityId;

    private UUID parentTaskId;

    private Timestamp startedDate;

    private Timestamp startTimeHas;

    private Timestamp dueDate;

    private Timestamp dueTimeHas;

    private Long estimateTime;

    private Timestamp activatedDate;
}
