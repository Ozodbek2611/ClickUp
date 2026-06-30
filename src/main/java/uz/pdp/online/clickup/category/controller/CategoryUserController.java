package uz.pdp.online.clickup.category.controller;

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
import uz.pdp.online.clickup.common.enums.TaskPermission;
import uz.pdp.online.clickup.category.dto.CategoryUserRequestDto;
import uz.pdp.online.clickup.category.dto.CategoryUserResponseDto;
import uz.pdp.online.clickup.category.service.CategoryUserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/category-user")
@RequiredArgsConstructor
@Tag(name = "Category User", description = "Category User APIs")
public class CategoryUserController {

    private final CategoryUserService categoryUserService;

    @Operation(summary = "Assign user to category", description = "Assigns a user to a category with permissions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User assigned successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"User assigned successfully\",\n  \"data\": {\n    \"categoryId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n    \"userId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa7\",\n    \"taskPermission\": \"EDITOR\"\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "400", description = "Validation Failed",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Validation Failed\",\n  \"data\": null,\n  \"errors\": [\"categoryId: must not be null\"]\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Category or user not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Category not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "409", description = "User already assigned to category",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"User already assigned to category\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<CategoryUserResponseDto>> assign(@Valid @RequestBody CategoryUserRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(categoryUserService.assign(dto), "User assigned successfully"));
    }

    @PatchMapping("/{categoryId}/{userId}")
    @Operation(summary = "Update category user permission", description = "Updates the permission level of a category member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permission updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Permission updated successfully\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Category user relation not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Category not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User removed successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"User removed successfully\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "User not assigned to category",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"User not assigned to category\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<Void>> unassign(@PathVariable UUID categoryId,
                                                         @PathVariable UUID userId) {
        categoryUserService.unassign(categoryId, userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "User removed successfully"));
    }

    @Operation(summary = "Get category members", description = "Returns all users assigned to a category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category users fetched successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Category users fetched successfully\",\n  \"data\": [\n    {\n      \"categoryId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n      \"userId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa7\",\n      \"taskPermission\": \"EDITOR\"\n    }\n  ],\n  \"errors\": null\n}")
                    ))
    })
    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponseDto<List<CategoryUserResponseDto>>> getByCategoryId(@PathVariable UUID categoryId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(categoryUserService.getByCategoryId(categoryId), "Category users fetched successfully"));
    }
}
