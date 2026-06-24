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
import uz.pdp.online.clickup.model.timeTrackedDto.TimeTrackedRequestDto;
import uz.pdp.online.clickup.model.timeTrackedDto.TimeTrackedResponseDto;
import uz.pdp.online.clickup.service.TimeTrackedService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/time-track")
@RequiredArgsConstructor
@Tag(name = "Time Tracked", description = "Time Tracked APIs")
public class TimeTrackedController {

    private final TimeTrackedService timeTrackedService;

    @Operation(summary = "Start time tracking", description = "Starts a time tracking session for a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Time tracking started successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Time tracking started successfully\",\n  \"data\": {\n    \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n    \"startedAt\": \"2026-06-24T10:00:00.000+00:00\",\n    \"stoppedAt\": null\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "400", description = "Validation Failed",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Validation Failed\",\n  \"data\": null,\n  \"errors\": [\"taskId: must not be null\"]\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Task not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    @PostMapping("/start")
    public ResponseEntity<ApiResponseDto<TimeTrackedResponseDto>> startTrack(@Valid @RequestBody TimeTrackedRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(timeTrackedService.startTrack(dto), "Time tracking started successfully"));
    }

    @Operation(summary = "Stop time tracking", description = "Stops an ongoing time tracking session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Time tracking stopped successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Time tracking stopped successfully\",\n  \"data\": {\n    \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n    \"startedAt\": \"2026-06-24T10:00:00.000+00:00\",\n    \"stoppedAt\": \"2026-06-24T11:30:00.000+00:00\"\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Time track record not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Time track record not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    @PutMapping("/stop/{id}")
    public ResponseEntity<ApiResponseDto<TimeTrackedResponseDto>> stopTrack(@PathVariable UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(timeTrackedService.stopTrack(id), "Time tracking stopped successfully"));
    }

    @Operation(summary = "Get time tracking history", description = "Returns all time tracking records for a specific task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Time tracking history fetched successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Time tracking history fetched successfully\",\n  \"data\": [\n    {\n      \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n      \"startedAt\": \"2026-06-24T10:00:00.000+00:00\",\n      \"stoppedAt\": \"2026-06-24T11:30:00.000+00:00\"\n    }\n  ],\n  \"errors\": null\n}")
                    ))
    })
    @GetMapping("/task/{taskId}")
    public ResponseEntity<ApiResponseDto<List<TimeTrackedResponseDto>>> getByTaskId(@PathVariable UUID taskId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(timeTrackedService.getByTaskId(taskId), "Time tracking history fetched successfully"));
    }
}
