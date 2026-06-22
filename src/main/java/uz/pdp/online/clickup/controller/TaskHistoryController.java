package uz.pdp.online.clickup.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.entity.audit.TaskHistory;
import uz.pdp.online.clickup.service.TaskHistoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/task-history")
@RequiredArgsConstructor
@Tag(name = "Task History", description = "Task History API")
public class TaskHistoryController {

    private final TaskHistoryService taskHistoryService;

        @Operation(summary = "Get task history", description = "Returns the full change history of a specific task")

    @GetMapping("/{taskId}")
    public ResponseEntity<List<TaskHistory>> getHistoryByTask(@PathVariable UUID taskId) {
        List<TaskHistory> history = taskHistoryService.getHistoryByTaskId(taskId);
        return ResponseEntity.ok(history);
    }
}
