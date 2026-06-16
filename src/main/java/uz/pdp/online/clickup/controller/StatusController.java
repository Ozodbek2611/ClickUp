package uz.pdp.online.clickup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.model.ApiResponse;
import uz.pdp.online.clickup.model.statusDto.StatusRequestDto;
import uz.pdp.online.clickup.model.statusDto.StatusResponseDto;
import uz.pdp.online.clickup.service.StatusService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/status")
@RequiredArgsConstructor
public class StatusController {

    private final StatusService statusService;

    @PostMapping
    public ResponseEntity<ApiResponse<StatusResponseDto>> create(@RequestBody StatusRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(statusService.create(dto), "Status created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StatusResponseDto>> edit(
            @PathVariable UUID id,
            @RequestBody StatusRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(statusService.edit(id, dto), "Status updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        statusService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(null, "Status deleted successfully"));
    }

    @GetMapping("/space/{spaceId}")
    public ResponseEntity<ApiResponse<List<StatusResponseDto>>> getBySpace(@PathVariable UUID spaceId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(statusService.getBySpaceId(spaceId), "Statuses fetched successfully"));
    }
}
