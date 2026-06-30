package uz.pdp.online.clickup.task.controller;

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
import uz.pdp.online.clickup.task.dto.TaskRequestDto;
import uz.pdp.online.clickup.task.dto.TaskResponseDto;
import uz.pdp.online.clickup.task.service.TaskService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
@Tag(name = "Task", description = "Task management APIs")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @Operation(summary = "Create task", description = "Creates a new task inside a category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Task created successfully\",\n  \"data\": {\n    \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n    \"name\": \"Design homepage\",\n    \"description\": \"Create the hero section\"\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "400", description = "Validation Failed",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Validation Failed\",\n  \"data\": null,\n  \"errors\": [\"name: must not be blank\"]\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Status, category, priority or parent task not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Category not found: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<TaskResponseDto>> create(@Valid @RequestBody TaskRequestDto dto) {
        TaskResponseDto response = taskService.create(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(response, "Task created successfully"));
    }

    @PatchMapping("/{taskId}/status/{statusId}")
    @Operation(summary = "Change task status", description = "Updates the status of a specific task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status changed successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Status changed successfully\",\n  \"data\": {\n    \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n    \"name\": \"Design homepage\"\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Task or status not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Task not found: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<TaskResponseDto>> changeStatus(@PathVariable UUID taskId,
                                                                        @PathVariable UUID statusId) {
        TaskResponseDto response = taskService.changeStatus(taskId, statusId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(response, "Status changed successfully"));
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get tasks by category", description = "Returns all tasks belonging to a specific category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks fetched successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Tasks fetched successfully\",\n  \"data\": [\n    {\n      \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n      \"name\": \"Design homepage\"\n    }\n  ],\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<List<TaskResponseDto>>> getByCategory(@PathVariable UUID categoryId) {
        List<TaskResponseDto> response = taskService.getByCategoryId(categoryId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(response, "Tasks fetched successfully"));
    }
}
