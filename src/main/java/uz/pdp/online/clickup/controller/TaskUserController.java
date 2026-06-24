package uz.pdp.online.clickup.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User assigned successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"User assigned successfully\",\n  \"data\": {\n    \"taskId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n    \"userId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa7\"\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "400", description = "Validation Failed",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Validation Failed\",\n  \"data\": null,\n  \"errors\": [\"taskId: must not be null\"]\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Task or user not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Task not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "409", description = "User is already assigned to the task",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"User [ID: 3fa85f64-5717-4562-b3fc-2c963f66afa7] is already assigned to Task [ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6]\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<TaskUserResponseDto>> assign(@Valid @RequestBody TaskUserRequestDto dto) {
        TaskUserResponseDto response = taskUserService.assign(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(response, "User assigned successfully"));
    }

    @DeleteMapping("/task/{taskId}/user/{userId}")
    @Operation(summary = "Unassign user from task", description = "Removes a user assignment from a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User unassigned successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"User unassigned successfully\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "User is not assigned to the task",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"User is not assigned to Task3fa85f64-5717-4562-b3fc-2c963f66afa7\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<Void>> unassign(@PathVariable UUID taskId,
                                                         @PathVariable UUID userId) {
        taskUserService.unassign(taskId, userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "User unassigned successfully"));
    }

    @Operation(summary = "Get assigned users", description = "Returns all users assigned to a specific task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assigned users fetched successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Assigned users fetched successfully\",\n  \"data\": [\n    {\n      \"taskId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n      \"userId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa7\"\n    }\n  ],\n  \"errors\": null\n}")
                    ))
    })
    @GetMapping("/task/{taskId}")
    public ResponseEntity<ApiResponseDto<List<TaskUserResponseDto>>> getByTask(@PathVariable UUID taskId) {
        List<TaskUserResponseDto> response = taskUserService.getByTaskId(taskId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(response, "Assigned users fetched successfully"));
    }
}
