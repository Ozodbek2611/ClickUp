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
import uz.pdp.online.clickup.model.taskDependencyDto.TaskDependencyRequestDto;
import uz.pdp.online.clickup.model.taskDependencyDto.TaskDependencyResponseDto;
import uz.pdp.online.clickup.service.TaskDependencyService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task-dependency")
@Tag(name = "Task Dependency", description = "Task Dependency APIs")
public class TaskDependencyController {

    private final TaskDependencyService taskDependencyService;

    @Operation(summary = "Create task dependency", description = "Creates a dependency relationship between two tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dependency Created",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Dependency Created\",\n  \"data\": {\n    \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n    \"taskId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa7\",\n    \"dependencyTaskId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa8\"\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "400", description = "Validation Failed",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Validation Failed\",\n  \"data\": null,\n  \"errors\": [\"taskId: must not be null\"]\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Task or dependency task not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Task not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa7\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "409", description = "Dependency already exists between the two tasks",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Dependency already exists between Task 3fa85f64-5717-4562-b3fc-2c963f66afa7 and Task 3fa85f64-5717-4562-b3fc-2c963f66afa8\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "500", description = "Task cannot depend on itself",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Internal server error\",\n  \"data\": null,\n  \"errors\": [\"Task cannot depend on itself\"]\n}")
                    ))
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<TaskDependencyResponseDto>> create(@RequestBody @Valid TaskDependencyRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(taskDependencyService.create(dto), "Dependency Created"));
    }

    @GetMapping("/get-task-dependencies/{taskId}")
    @Operation(summary = "Get task dependencies", description = "Returns all dependencies for a specific task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task dependencies",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Task dependencies\",\n  \"data\": [\n    {\n      \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n      \"taskId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa7\",\n      \"dependencyTaskId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa8\"\n    }\n  ],\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<List<TaskDependencyResponseDto>>> getTaskById(@PathVariable @Valid UUID taskId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(taskDependencyService.getByTaskId(taskId), "Task dependencies"));
    }

    @DeleteMapping("/delete/{taskId}")
    @Operation(summary = "Delete task dependency", description = "Removes a dependency relationship")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task dependency deleted",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Task dependency deleted\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Task Dependency not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Task Dependency not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<Void>> delete(@PathVariable @Valid UUID taskId) {
        taskDependencyService.delete(taskId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "Task dependency deleted"));
    }
}
