package uz.pdp.online.clickup.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.common.ApiResponseDto;
import uz.pdp.online.clickup.model.priorityDto.PriorityRequestDto;
import uz.pdp.online.clickup.model.priorityDto.PriorityResponseDto;
import uz.pdp.online.clickup.service.PriorityService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/priority")
@RequiredArgsConstructor
@Tag(name = "Priority", description = "Priority management APIs")
public class PriorityController {

    private final PriorityService priorityService;

        @Operation(summary = "Create priority", description = "Creates a new priority level")

    @PostMapping
    public ResponseEntity<ApiResponseDto<PriorityResponseDto>> create(@Valid @RequestBody PriorityRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(priorityService.create(dto), "Priority created successfully"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edit priority", description = "Updates an existing priority level")
    public ResponseEntity<ApiResponseDto<PriorityResponseDto>> edit(@PathVariable UUID id,
                                                                 @Valid @RequestBody PriorityRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(priorityService.edit(id, dto), "Priority updated successfully"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete priority", description = "Permanently deletes a priority level")
    public ResponseEntity<ApiResponseDto<Void>> delete(@PathVariable UUID id) {
        priorityService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "Priority deleted successfully"));
    }

    @GetMapping
    @Operation(summary = "Get all priorities", description = "Returns all available priority levels")
    public ResponseEntity<ApiResponseDto<List<PriorityResponseDto>>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(priorityService.getAll(), "Priorities fetched successfully"));
    }
}
