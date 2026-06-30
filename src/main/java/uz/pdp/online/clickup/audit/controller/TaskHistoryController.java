package uz.pdp.online.clickup.audit.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.online.clickup.audit.entity.TaskHistory;
import uz.pdp.online.clickup.audit.service.TaskHistoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/task-history")
@RequiredArgsConstructor
@Tag(name = "Task History", description = "Task History API")
public class TaskHistoryController {

    private final TaskHistoryService taskHistoryService;

    @Operation(summary = "Get task history", description = "Returns the full change history of a specific task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task history fetched successfully (raw list, not wrapped in ApiResponseDto)",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "[\n  {\n    \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n    \"changeFieldName\": \"status\",\n    \"beforeValue\": \"TODO\",\n    \"afterValue\": \"IN_PROGRESS\",\n    \"historyType\": \"UPDATE\"\n  }\n]")
                    ))
    })
    @GetMapping("/{taskId}")
    public ResponseEntity<List<TaskHistory>> getHistoryByTask(@PathVariable UUID taskId) {
        List<TaskHistory> history = taskHistoryService.getHistoryByTaskId(taskId);
        return ResponseEntity.ok(history);
    }
}
