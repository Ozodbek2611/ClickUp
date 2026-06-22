package uz.pdp.online.clickup.model.taskDto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Task details returned from the API")
public class TaskResponseDto {

    @Schema(description = "Unique identifier of the task", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "Title of the task", example = "Implement login API")
    private String name;

    @Schema(description = "Detailed description", example = "Create a JWT-based login endpoint")
    private String description;

    @Schema(description = "ID of the current status", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID statusId;

    @Schema(description = "Name of the current status", example = "In Progress")
    private String statusName;

    @Schema(description = "ID of the parent category", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID categoryId;

    @Schema(description = "ID of the assigned priority", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID priorityId;

    @Schema(description = "ID of the parent task if this is a subtask", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID parentTaskId;

    @Schema(description = "Scheduled start date")
    private Timestamp startedDate;

    @Schema(description = "Specific start time")
    private Timestamp startTimeHas;

    @Schema(description = "Deadline date")
    private Timestamp dueDate;

    @Schema(description = "Specific deadline time")
    private Timestamp dueTimeHas;

    @Schema(description = "Estimated completion time in minutes", example = 120)
    private Long estimateTime;

    @Schema(description = "Date when task was activated")
    private Timestamp activatedDate;

    @Schema(description = "Timestamp when the task was created")
    private Timestamp createdAt;

    @Schema(description = "Timestamp when the task was last updated")
    private Timestamp updatedAt;
}
