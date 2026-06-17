package uz.pdp.online.clickup.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.entity.enums.TaskPermission;
import uz.pdp.online.clickup.model.ApiResponse;
import uz.pdp.online.clickup.model.categoryUserDto.CategoryUserRequestDto;
import uz.pdp.online.clickup.model.categoryUserDto.CategoryUserResponseDto;
import uz.pdp.online.clickup.service.CategoryUserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/category-user")
@RequiredArgsConstructor
public class CategoryUserController {

    private final CategoryUserService categoryUserService;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryUserResponseDto>> assign(@Valid @RequestBody CategoryUserRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(categoryUserService.assign(dto), "User assigned successfully"));
    }

    @PatchMapping("/{categoryId}/{userId}")
    public ResponseEntity<ApiResponse<Void>> updatePermission(@PathVariable UUID categoryId,
                                                              @PathVariable UUID userId,
                                                              @RequestParam TaskPermission taskPermission) {
        categoryUserService.updatePermission(categoryId, userId, taskPermission);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(null, "Permission updated successfully"));
    }

    @DeleteMapping("/{categoryId}/{userId}")
    public ResponseEntity<ApiResponse<Void>> unassign(@PathVariable UUID categoryId,
                                                      @PathVariable UUID userId) {
        categoryUserService.unassign(categoryId, userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(null, "User removed successfully"));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<List<CategoryUserResponseDto>>> getByCategoryId(@PathVariable UUID categoryId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(categoryUserService.getByCategoryId(categoryId), "Category users fetched successfully"));
    }
}
