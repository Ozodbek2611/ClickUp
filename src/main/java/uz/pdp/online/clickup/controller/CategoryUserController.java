package uz.pdp.online.clickup.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.entity.enums.TaskPermission;
import uz.pdp.online.clickup.common.ApiResponseDto;
import uz.pdp.online.clickup.model.categoryUserDto.CategoryUserRequestDto;
import uz.pdp.online.clickup.model.categoryUserDto.CategoryUserResponseDto;
import uz.pdp.online.clickup.service.CategoryUserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/category-user")
@RequiredArgsConstructor
@Tag(name = "Category User", description = "Category User APIs")
public class CategoryUserController {

    private final CategoryUserService categoryUserService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<CategoryUserResponseDto>> assign(@Valid @RequestBody CategoryUserRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(categoryUserService.assign(dto), "User assigned successfully"));
    }

    @PatchMapping("/{categoryId}/{userId}")
    public ResponseEntity<ApiResponseDto<Void>> updatePermission(@PathVariable UUID categoryId,
                                                              @PathVariable UUID userId,
                                                              @RequestParam TaskPermission taskPermission) {
        categoryUserService.updatePermission(categoryId, userId, taskPermission);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "Permission updated successfully"));
    }

    @DeleteMapping("/{categoryId}/{userId}")
    public ResponseEntity<ApiResponseDto<Void>> unassign(@PathVariable UUID categoryId,
                                                      @PathVariable UUID userId) {
        categoryUserService.unassign(categoryId, userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "User removed successfully"));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponseDto<List<CategoryUserResponseDto>>> getByCategoryId(@PathVariable UUID categoryId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(categoryUserService.getByCategoryId(categoryId), "Category users fetched successfully"));
    }
}
