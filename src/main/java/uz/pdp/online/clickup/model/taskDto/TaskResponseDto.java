package uz.pdp.online.clickup.model.taskDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDto {

    private UUID id;

    private String name;

    private String description;

    private UUID statusId;

    private String statusName;

    private UUID categoryId;

    private UUID priorityId;

    private UUID parentTaskId;

    private Timestamp startedDate;

    private Timestamp startTimeHas;

    private Timestamp dueDate;

    private Timestamp dueTimeHas;

    private Long estimateTime;

    private Timestamp activatedDate;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
