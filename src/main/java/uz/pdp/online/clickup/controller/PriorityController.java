package uz.pdp.online.clickup.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.model.ApiResponse;
import uz.pdp.online.clickup.model.priorityDto.PriorityRequestDto;
import uz.pdp.online.clickup.model.priorityDto.PriorityResponseDto;
import uz.pdp.online.clickup.service.PriorityService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/priority")
@RequiredArgsConstructor
public class PriorityController {

    private final PriorityService priorityService;

    @PostMapping
    public ResponseEntity<ApiResponse<PriorityResponseDto>> create(@Valid @RequestBody PriorityRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(priorityService.create(dto), "Priority created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PriorityResponseDto>> edit(@PathVariable UUID id,
                                                                 @Valid @RequestBody PriorityRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(priorityService.edit(id, dto), "Priority updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        priorityService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(null, "Priority deleted successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PriorityResponseDto>>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(priorityService.getAll(), "Priorities fetched successfully"));
    }
}
