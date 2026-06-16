package uz.pdp.online.clickup.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.model.ApiResponse;
import uz.pdp.online.clickup.model.iconDto.IconRequestDto;
import uz.pdp.online.clickup.model.iconDto.IconResponseDto;
import uz.pdp.online.clickup.service.IconService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/icon")
@RequiredArgsConstructor
public class IconController {

    private final IconService iconService;

    @PostMapping
    public ResponseEntity<ApiResponse<IconResponseDto>> create(@Valid @RequestBody IconRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(iconService.create(dto), "Icon created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<IconResponseDto>> edit(@PathVariable UUID id,
                                                             @Valid @RequestBody IconRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(iconService.edit(id, dto), "Icon updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        iconService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(null, "Icon deleted successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<IconResponseDto>>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(iconService.getAll(), "Icons fetched successfully"));
    }
}
