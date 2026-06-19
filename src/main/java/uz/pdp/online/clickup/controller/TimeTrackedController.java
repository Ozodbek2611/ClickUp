package uz.pdp.online.clickup.controller;

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

    @PostMapping("/start")
    public ResponseEntity<ApiResponseDto<TimeTrackedResponseDto>> startTrack(@Valid @RequestBody TimeTrackedRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(timeTrackedService.startTrack(dto), "Time tracking started successfully"));
    }

    @PutMapping("/stop/{id}")
    public ResponseEntity<ApiResponseDto<TimeTrackedResponseDto>> stopTrack(@PathVariable UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(timeTrackedService.stopTrack(id), "Time tracking stopped successfully"));
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<ApiResponseDto<List<TimeTrackedResponseDto>>> getByTaskId(@PathVariable UUID taskId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(timeTrackedService.getByTaskId(taskId), "Time tracking history fetched successfully"));
    }
}
