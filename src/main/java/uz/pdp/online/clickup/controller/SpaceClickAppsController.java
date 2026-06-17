package uz.pdp.online.clickup.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.model.ApiResponse;
import uz.pdp.online.clickup.model.spaceClickAppsDto.SpaceClickAppsRequestDto;
import uz.pdp.online.clickup.model.spaceClickAppsDto.SpaceClickAppsResponseDto;
import uz.pdp.online.clickup.service.SpaceClickAppsService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/space-click-apps")
public class SpaceClickAppsController {

    private final SpaceClickAppsService spaceClickAppsService;

    @PostMapping
    public ResponseEntity<ApiResponse<SpaceClickAppsResponseDto>> create(@RequestBody @Valid SpaceClickAppsRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(spaceClickAppsService.create(dto), "ClickApps added to Space"));
    }

    @GetMapping("/{spaceId}")
    public ResponseEntity<ApiResponse<List<SpaceClickAppsResponseDto>>> getBySpaceId(@PathVariable UUID spaceId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(spaceClickAppsService.getBySpaceId(spaceId), "Space ClickApps"));
    }

    @DeleteMapping("/{spaceId}/{clickAppsId}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID spaceId, @PathVariable UUID clickAppsId) {
        spaceClickAppsService.delete(spaceId, clickAppsId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(null, "ClickApps removed from Space"));
    }
}