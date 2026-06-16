package uz.pdp.online.clickup.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.model.ApiResponse;
import uz.pdp.online.clickup.model.categoryDto.CategoryRequestDto;
import uz.pdp.online.clickup.model.categoryDto.CategoryResponseDto;
import uz.pdp.online.clickup.model.categoryDto.CategoryUpdateDto;
import uz.pdp.online.clickup.service.CategoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponseDto>> create(@Valid @RequestBody CategoryRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(categoryService.create(dto), "Category created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDto>> edit(@PathVariable UUID id,
                                                                 @Valid @RequestBody CategoryUpdateDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(categoryService.edit(id, dto), "Category updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        categoryService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(null, "Category deleted successfully"));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ApiResponse<List<CategoryResponseDto>>> getByProjectId(@PathVariable UUID projectId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(categoryService.getByProjectId(projectId), "Categories fetched successfully"));
    }
}