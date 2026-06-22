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

        @Operation(summary = "Assign user to category", description = "Assigns a user to a category with permissions")

    @PostMapping
    public ResponseEntity<ApiResponseDto<CategoryUserResponseDto>> assign(@Valid @RequestBody CategoryUserRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(categoryUserService.assign(dto), "User assigned successfully"));
    }

    @PatchMapping("/{categoryId}/{userId}")
    @Operation(summary = "Update category user permission", description = "Updates the permission level of a category member")
    public ResponseEntity<ApiResponseDto<Void>> updatePermission(@PathVariable UUID categoryId,
                                                              @PathVariable UUID userId,
                                                              @RequestParam TaskPermission taskPermission) {
        categoryUserService.updatePermission(categoryId, userId, taskPermission);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "Permission updated successfully"));
    }

    @DeleteMapping("/{categoryId}/{userId}")
    @Operation(summary = "Remove user from category", description = "Removes a user from a category")
    public ResponseEntity<ApiResponseDto<Void>> unassign(@PathVariable UUID categoryId,
                                                      @PathVariable UUID userId) {
        categoryUserService.unassign(categoryId, userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "User removed successfully"));
    }

        @Operation(summary = "Get category members", description = "Returns all users assigned to a category")

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponseDto<List<CategoryUserResponseDto>>> getByCategoryId(@PathVariable UUID categoryId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(categoryUserService.getByCategoryId(categoryId), "Category users fetched successfully"));
    }
}
