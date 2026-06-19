package uz.pdp.online.clickup.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.common.ApiResponseDto;
import uz.pdp.online.clickup.model.statusDto.StatusRequestDto;
import uz.pdp.online.clickup.model.statusDto.StatusResponseDto;
import uz.pdp.online.clickup.service.StatusService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/status")
@RequiredArgsConstructor
@Tag(name = "Status", description = "Status APIs")
public class StatusController {

    private final StatusService statusService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<StatusResponseDto>> create(@RequestBody StatusRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(statusService.create(dto), "Status created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<StatusResponseDto>> edit(
            @PathVariable UUID id,
            @RequestBody StatusRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(statusService.edit(id, dto), "Status updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> delete(@PathVariable UUID id) {
        statusService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "Status deleted successfully"));
    }

    @GetMapping("/space/{spaceId}")
    public ResponseEntity<ApiResponseDto<List<StatusResponseDto>>> getBySpace(@PathVariable UUID spaceId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(statusService.getBySpaceId(spaceId), "Statuses fetched successfully"));
    }
}
