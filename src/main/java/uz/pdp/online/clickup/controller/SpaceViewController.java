package uz.pdp.online.clickup.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.model.ApiResponse;
import uz.pdp.online.clickup.model.spaceViewDto.SpaceViewRequestDto;
import uz.pdp.online.clickup.model.spaceViewDto.SpaceViewResponseDto;
import uz.pdp.online.clickup.service.SpaceViewService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/space-view")
public class SpaceViewController {

    private final SpaceViewService spaceViewService;

    @PostMapping
    public ResponseEntity<ApiResponse<SpaceViewResponseDto>> create(@RequestBody @Valid SpaceViewRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(spaceViewService.create(dto), "View added to Space"));
    }

    @GetMapping("/{spaceId}")
    public ResponseEntity<ApiResponse<List<SpaceViewResponseDto>>> getBySpaceId(@PathVariable UUID spaceId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(spaceViewService.getBySpaceId(spaceId), "Space Views"));
    }

    @DeleteMapping("/{spaceId}/{viewId}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID spaceId, @PathVariable UUID viewId) {
        spaceViewService.delete(spaceId, viewId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(null, "View removed from Space"));
    }
}
