package uz.pdp.online.clickup.model.taskUserDto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Task user assignment details")
public class TaskUserResponseDto {

    @Schema(description = "Unique identifier of the assignment", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "ID of the task", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID taskId;

    @Schema(description = "ID of the assigned user", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID userId;

    @Schema(description = "Email of the assigned user", example = "john@gmail.com")
    private String userEmail;

    @Schema(description = "Timestamp when the assignment was created")
    private Timestamp createdAt;
}
