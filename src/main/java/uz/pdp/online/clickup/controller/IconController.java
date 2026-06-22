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

    @PostMapping
    public ResponseEntity<ApiResponseDto<IconResponseDto>> create(@Valid @RequestBody IconRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(iconService.create(dto), "Icon created successfully"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edit icon", description = "Updates an existing icon")
    public ResponseEntity<ApiResponseDto<IconResponseDto>> edit(@PathVariable UUID id,
                                                             @Valid @RequestBody IconRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(iconService.edit(id, dto), "Icon updated successfully"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete icon", description = "Permanently deletes an icon")
    public ResponseEntity<ApiResponseDto<Void>> delete(@PathVariable UUID id) {
        iconService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "Icon deleted successfully"));
    }

    @GetMapping
    @Operation(summary = "Get all icons", description = "Returns all available icons")
    public ResponseEntity<ApiResponseDto<List<IconResponseDto>>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(iconService.getAll(), "Icons fetched successfully"));
    }
}
