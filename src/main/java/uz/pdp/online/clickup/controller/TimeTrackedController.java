package uz.pdp.online.clickup.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.model.ApiResponse;
import uz.pdp.online.clickup.model.timeTrackedDto.TimeTrackedRequestDto;
import uz.pdp.online.clickup.model.timeTrackedDto.TimeTrackedResponseDto;
import uz.pdp.online.clickup.service.TimeTrackedService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/time-track")
@RequiredArgsConstructor
public class TimeTrackedController {

    private final TimeTrackedService timeTrackedService;

    @PostMapping("/start")
    public ResponseEntity<ApiResponse<TimeTrackedResponseDto>> startTrack(@Valid @RequestBody TimeTrackedRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(timeTrackedService.startTrack(dto), "Time tracking started successfully"));
    }

    @PutMapping("/stop/{id}")
    public ResponseEntity<ApiResponse<TimeTrackedResponseDto>> stopTrack(@PathVariable UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(timeTrackedService.stopTrack(id), "Time tracking stopped successfully"));
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<ApiResponse<List<TimeTrackedResponseDto>>> getByTaskId(@PathVariable UUID taskId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(timeTrackedService.getByTaskId(taskId), "Time tracking history fetched successfully"));
    }
}
