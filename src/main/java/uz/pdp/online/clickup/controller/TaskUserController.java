package uz.pdp.online.clickup.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.model.ApiResponse;
import uz.pdp.online.clickup.model.taskUserDto.TaskUserRequestDto;
import uz.pdp.online.clickup.model.taskUserDto.TaskUserResponseDto;
import uz.pdp.online.clickup.service.TaskUserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/task-user")
@RequiredArgsConstructor
@Slf4j
public class TaskUserController {

    private final TaskUserService taskUserService;

    @PostMapping
    public ResponseEntity<ApiResponse<TaskUserResponseDto>> assign(@Valid @RequestBody TaskUserRequestDto dto) {
        TaskUserResponseDto response = taskUserService.assign(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(response, "User assigned successfully"));
    }

    @DeleteMapping("/task/{taskId}/user/{userId}")
    public ResponseEntity<ApiResponse<Void>> unassign(@PathVariable UUID taskId,
                                                      @PathVariable UUID userId) {
        taskUserService.unassign(taskId, userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(null, "User unassigned successfully"));
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<ApiResponse<List<TaskUserResponseDto>>> getByTask(@PathVariable UUID taskId) {
        List<TaskUserResponseDto> response = taskUserService.getByTaskId(taskId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(response, "Assigned users fetched successfully"));
    }
}
