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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "View added to Space",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"View added to Space\",\n  \"data\": {\n    \"spaceId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n    \"viewId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa7\"\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "400", description = "Validation Failed",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Validation Failed\",\n  \"data\": null,\n  \"errors\": [\"spaceId: must not be null\"]\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Space or view not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Space not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "409", description = "View already added to this space",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"View already added to this space\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<SpaceViewResponseDto>> create(@RequestBody @Valid SpaceViewRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(spaceViewService.create(dto), "View added to Space"));
    }

    @Operation(summary = "Get space views", description = "Returns all views configured for a space")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Space Views",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Space Views\",\n  \"data\": [\n    {\n      \"spaceId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n      \"viewId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa7\"\n    }\n  ],\n  \"errors\": null\n}")
                    ))
    })
    @GetMapping("/{spaceId}")
    public ResponseEntity<ApiResponseDto<List<SpaceViewResponseDto>>> getBySpaceId(@PathVariable UUID spaceId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(spaceViewService.getBySpaceId(spaceId), "Space Views"));
    }

    @DeleteMapping("/{spaceId}/{viewId}")
    @Operation(summary = "Remove view from space", description = "Removes a view from a space")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "View removed from Space",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"View removed from Space\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Space View relation not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Space View not found\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<Void>> delete(@PathVariable UUID spaceId, @PathVariable UUID viewId) {
        spaceViewService.delete(spaceId, viewId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "View removed from Space"));
    }
}
