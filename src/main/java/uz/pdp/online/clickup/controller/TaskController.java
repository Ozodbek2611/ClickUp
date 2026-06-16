package uz.pdp.online.clickup.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.model.ApiResponse;
import uz.pdp.online.clickup.model.taskDto.TaskRequestDto;
import uz.pdp.online.clickup.model.taskDto.TaskResponseDto;
import uz.pdp.online.clickup.service.TaskService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
@Slf4j
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<ApiResponse<TaskResponseDto>> create(@Valid @RequestBody TaskRequestDto dto) {
        TaskResponseDto response = taskService.create(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(response, "Task created successfully"));
    }

    @PatchMapping("/{taskId}/status/{statusId}")
    public ResponseEntity<ApiResponse<TaskResponseDto>> changeStatus(@PathVariable UUID taskId,
                                                                     @PathVariable UUID statusId) {
        TaskResponseDto response = taskService.changeStatus(taskId, statusId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(response, "Status changed successfully"));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<TaskResponseDto>>> getByCategory(@PathVariable UUID categoryId) {
        List<TaskResponseDto> response = taskService.getByCategoryId(categoryId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(response, "Tasks fetched successfully"));
    }
}
