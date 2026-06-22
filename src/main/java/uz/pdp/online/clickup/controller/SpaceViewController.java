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
import uz.pdp.online.clickup.model.spaceViewDto.SpaceViewRequestDto;
import uz.pdp.online.clickup.model.spaceViewDto.SpaceViewResponseDto;
import uz.pdp.online.clickup.service.SpaceViewService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/space-view")
@Tag(name = "Space View", description = "Space View APIs")
public class SpaceViewController {

    private final SpaceViewService spaceViewService;

        @Operation(summary = "Add view to space", description = "Adds a new view type to a space")

    @PostMapping
    public ResponseEntity<ApiResponseDto<SpaceViewResponseDto>> create(@RequestBody @Valid SpaceViewRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(spaceViewService.create(dto), "View added to Space"));
    }

        @Operation(summary = "Get space views", description = "Returns all views configured for a space")

    @GetMapping("/{spaceId}")
    public ResponseEntity<ApiResponseDto<List<SpaceViewResponseDto>>> getBySpaceId(@PathVariable UUID spaceId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(spaceViewService.getBySpaceId(spaceId), "Space Views"));
    }

    @DeleteMapping("/{spaceId}/{viewId}")
    @Operation(summary = "Remove view from space", description = "Removes a view from a space")
    public ResponseEntity<ApiResponseDto<Void>> delete(@PathVariable UUID spaceId, @PathVariable UUID viewId) {
        spaceViewService.delete(spaceId, viewId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "View removed from Space"));
    }
}
