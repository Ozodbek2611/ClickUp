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
import uz.pdp.online.clickup.model.iconDto.IconRequestDto;
import uz.pdp.online.clickup.model.iconDto.IconResponseDto;
import uz.pdp.online.clickup.service.IconService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/icon")
@RequiredArgsConstructor
@Tag(name = "Icon", description = "Icon APIs")
public class IconController {

    private final IconService iconService;

    @Operation(summary = "Create icon", description = "Creates a new icon entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Icon created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Icon created successfully\",\n  \"data\": {\n    \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n    \"color\": \"#FF5733\",\n    \"initialLetter\": \"A\"\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "400", description = "Validation Failed",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Validation Failed\",\n  \"data\": null,\n  \"errors\": [\"color: must not be blank\"]\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Attachment not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Attachment not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<IconResponseDto>> create(@Valid @RequestBody IconRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(iconService.create(dto), "Icon created successfully"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edit icon", description = "Updates an existing icon")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Icon updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Icon updated successfully\",\n  \"data\": {\n    \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n    \"color\": \"#33A1FF\",\n    \"initialLetter\": \"B\"\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "400", description = "Validation Failed",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Validation Failed\",\n  \"data\": null,\n  \"errors\": [\"color: must not be blank\"]\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Icon or attachment not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Icon not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<IconResponseDto>> edit(@PathVariable UUID id,
                                                                @Valid @RequestBody IconRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(iconService.edit(id, dto), "Icon updated successfully"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete icon", description = "Permanently deletes an icon")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Icon deleted successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Icon deleted successfully\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Icon not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Icon not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<Void>> delete(@PathVariable UUID id) {
        iconService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "Icon deleted successfully"));
    }

    @GetMapping
    @Operation(summary = "Get all icons", description = "Returns all available icons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Icons fetched successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Icons fetched successfully\",\n  \"data\": [\n    {\n      \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n      \"color\": \"#FF5733\",\n      \"initialLetter\": \"A\"\n    }\n  ],\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<List<IconResponseDto>>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(iconService.getAll(), "Icons fetched successfully"));
    }
}
