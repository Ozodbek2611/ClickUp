package uz.pdp.online.clickup.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "ClickApps added to Space",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"ClickApps added to Space\",\n  \"data\": {\n    \"spaceId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n    \"clickAppsId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa7\"\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "400", description = "Validation Failed",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Validation Failed\",\n  \"data\": null,\n  \"errors\": [\"spaceId: must not be null\"]\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Space or ClickApps not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Space not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "409", description = "ClickApps already added to this space",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"ClickApps already added to this space\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<SpaceClickAppsResponseDto>> create(@RequestBody @Valid SpaceClickAppsRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(spaceClickAppsService.create(dto), "ClickApps added to Space"));
    }

    @Operation(summary = "Get space ClickApps", description = "Returns all ClickApps enabled for a space")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Space ClickApps",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Space ClickApps\",\n  \"data\": [\n    {\n      \"spaceId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n      \"clickAppsId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa7\"\n    }\n  ],\n  \"errors\": null\n}")
                    ))
    })
    @GetMapping("/{spaceId}")
    public ResponseEntity<ApiResponseDto<List<SpaceClickAppsResponseDto>>> getBySpaceId(@PathVariable UUID spaceId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(spaceClickAppsService.getBySpaceId(spaceId), "Space ClickApps"));
    }

    @DeleteMapping("/{spaceId}/{clickAppsId}")
    @Operation(summary = "Remove ClickApp from space", description = "Disables a ClickApp from a space")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ClickApps removed from Space",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"ClickApps removed from Space\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Space ClickApps relation not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Space ClickApps not found\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<Void>> delete(@PathVariable UUID spaceId, @PathVariable UUID clickAppsId) {
        spaceClickAppsService.delete(spaceId, clickAppsId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "ClickApps removed from Space"));
    }
}