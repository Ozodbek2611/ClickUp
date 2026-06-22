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
import uz.pdp.online.clickup.model.spaceClickAppsDto.SpaceClickAppsRequestDto;
import uz.pdp.online.clickup.model.spaceClickAppsDto.SpaceClickAppsResponseDto;
import uz.pdp.online.clickup.service.SpaceClickAppsService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/space-click-apps")
@Tag(name = "Space ClickApps", description = "Space ClickApps APIs")
public class SpaceClickAppsController {

    private final SpaceClickAppsService spaceClickAppsService;

        @Operation(summary = "Add ClickApp to space", description = "Enables a ClickApp feature for a space")

    @PostMapping
    public ResponseEntity<ApiResponseDto<SpaceClickAppsResponseDto>> create(@RequestBody @Valid SpaceClickAppsRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(spaceClickAppsService.create(dto), "ClickApps added to Space"));
    }

        @Operation(summary = "Get space ClickApps", description = "Returns all ClickApps enabled for a space")

    @GetMapping("/{spaceId}")
    public ResponseEntity<ApiResponseDto<List<SpaceClickAppsResponseDto>>> getBySpaceId(@PathVariable UUID spaceId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(spaceClickAppsService.getBySpaceId(spaceId), "Space ClickApps"));
    }

    @DeleteMapping("/{spaceId}/{clickAppsId}")
    @Operation(summary = "Remove ClickApp from space", description = "Disables a ClickApp from a space")
    public ResponseEntity<ApiResponseDto<Void>> delete(@PathVariable UUID spaceId, @PathVariable UUID clickAppsId) {
        spaceClickAppsService.delete(spaceId, clickAppsId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "ClickApps removed from Space"));
    }
}