package uz.pdp.online.clickup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.entity.TaskHistory;
import uz.pdp.online.clickup.service.TaskHistoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/task-history")
@RequiredArgsConstructor
public class TaskHistoryController {

    private final TaskHistoryService taskHistoryService;

    @GetMapping("/{taskId}")
    public ResponseEntity<List<TaskHistory>> getHistoryByTask(@PathVariable UUID taskId) {
        List<TaskHistory> history = taskHistoryService.getHistoryByTaskId(taskId);
        return ResponseEntity.ok(history);
    }
}
