package uz.pdp.online.clickup.model.taskDto;

import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(description = "Request body for creating a new task")
public class TaskRequestDto {

    @NotBlank(message = "Task name cannot be blank")
    @Schema(description = "Title of the task", example = "Implement login API")
    private String name;

    @Schema(description = "Detailed description of the task", example = "Create a JWT-based login endpoint")
    private String description;

    @NotNull(message = "Status ID is required")
    @Schema(description = "ID of the initial status", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID statusId;

    @NotNull(message = "Category ID is required")
    @Schema(description = "ID of the category (list) the task belongs to", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID categoryId;

    @Schema(description = "ID of the priority level (optional)", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID priorityId;

    @Schema(description = "ID of the parent task for subtasks (optional)", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID parentTaskId;

    @Schema(description = "Scheduled start date")
    private Timestamp startedDate;

    @Schema(description = "Specific start time")
    private Timestamp startTimeHas;

    @Schema(description = "Deadline date for the task")
    private Timestamp dueDate;

    @Schema(description = "Specific deadline time")
    private Timestamp dueTimeHas;

    @Schema(description = "Estimated time to complete in minutes", example = 120)
    private Long estimateTime;

    @Schema(description = "Date when the task was activated")
    private Timestamp activatedDate;
}
