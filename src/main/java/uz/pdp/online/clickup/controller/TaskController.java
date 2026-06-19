package uz.pdp.online.clickup.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.common.ApiResponseDto;
import uz.pdp.online.clickup.model.taskDto.TaskRequestDto;
import uz.pdp.online.clickup.model.taskDto.TaskResponseDto;
import uz.pdp.online.clickup.service.TaskService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
@Tag(name = "Task", description = "Task APIs")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<TaskResponseDto>> create(@Valid @RequestBody TaskRequestDto dto) {
        TaskResponseDto response = taskService.create(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(response, "Task created successfully"));
    }

    @PatchMapping("/{taskId}/status/{statusId}")
    public ResponseEntity<ApiResponseDto<TaskResponseDto>> changeStatus(@PathVariable UUID taskId,
                                                                     @PathVariable UUID statusId) {
        TaskResponseDto response = taskService.changeStatus(taskId, statusId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(response, "Status changed successfully"));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponseDto<List<TaskResponseDto>>> getByCategory(@PathVariable UUID categoryId) {
        List<TaskResponseDto> response = taskService.getByCategoryId(categoryId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(response, "Tasks fetched successfully"));
    }
}
