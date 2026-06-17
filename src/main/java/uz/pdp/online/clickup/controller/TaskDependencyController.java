package uz.pdp.online.clickup.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.model.ApiResponse;
import uz.pdp.online.clickup.model.taskDependencyDto.TaskDependencyRequestDto;
import uz.pdp.online.clickup.model.taskDependencyDto.TaskDependencyResponseDto;
import uz.pdp.online.clickup.service.TaskDependencyService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task-dependency")
public class TaskDependencyController {

    private final TaskDependencyService taskDependencyService;

    @PostMapping
    public ResponseEntity<ApiResponse<TaskDependencyResponseDto>> create(@RequestBody @Valid TaskDependencyRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(taskDependencyService.create(dto), "Dependency Created"));
    }

    @GetMapping("/get-task-dependencies/{taskId}")
    public ResponseEntity<ApiResponse<List<TaskDependencyResponseDto>>> getTaskById(@PathVariable @Valid UUID taskId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(taskDependencyService.getByTaskId(taskId), "Task dependencies"));
    }

    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable @Valid UUID taskId) {
        taskDependencyService.delete(taskId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(null, "Task dependency deleted"));
    }
}
