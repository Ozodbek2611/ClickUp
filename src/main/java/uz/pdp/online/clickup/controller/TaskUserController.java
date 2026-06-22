package uz.pdp.online.clickup.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.common.ApiResponseDto;
import uz.pdp.online.clickup.model.taskUserDto.TaskUserRequestDto;
import uz.pdp.online.clickup.model.taskUserDto.TaskUserResponseDto;
import uz.pdp.online.clickup.service.TaskUserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/task-user")
@RequiredArgsConstructor
@Tag(name = "Task User", description = "Task User APIs")
public class TaskUserController {

    private final TaskUserService taskUserService;

        @Operation(summary = "Assign user to task", description = "Assigns a user to a task")

    @PostMapping
    public ResponseEntity<ApiResponseDto<TaskUserResponseDto>> assign(@Valid @RequestBody TaskUserRequestDto dto) {
        TaskUserResponseDto response = taskUserService.assign(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(response, "User assigned successfully"));
    }

    @DeleteMapping("/task/{taskId}/user/{userId}")
    @Operation(summary = "Unassign user from task", description = "Removes a user assignment from a task")
    public ResponseEntity<ApiResponseDto<Void>> unassign(@PathVariable UUID taskId,
                                                      @PathVariable UUID userId) {
        taskUserService.unassign(taskId, userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "User unassigned successfully"));
    }

        @Operation(summary = "Get assigned users", description = "Returns all users assigned to a specific task")

    @GetMapping("/task/{taskId}")
    public ResponseEntity<ApiResponseDto<List<TaskUserResponseDto>>> getByTask(@PathVariable UUID taskId) {
        List<TaskUserResponseDto> response = taskUserService.getByTaskId(taskId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(response, "Assigned users fetched successfully"));
    }
}
