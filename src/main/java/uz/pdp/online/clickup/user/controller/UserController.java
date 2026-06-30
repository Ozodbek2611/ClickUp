package uz.pdp.online.clickup.user.controller;

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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.common.ApiResponseDto;
import uz.pdp.online.clickup.user.entity.User;
import uz.pdp.online.clickup.user.service.UserService;
import uz.pdp.online.clickup.user.dto.UserResponseDto;
import uz.pdp.online.clickup.user.dto.UserUpdateDto;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "User profile management APIs")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Get current user", description = "Returns profile information of the authenticated user")
    @ApiResponse(responseCode = "200", description = "Profile fetched successfully",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Profile fetched successfully\",\n  \"data\": {\n    \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n    \"fullName\": \"John Doe\",\n    \"email\": \"john@example.com\",\n    \"color\": \"#2ECC71\"\n  },\n  \"errors\": null\n}")
            ))
    public ResponseEntity<ApiResponseDto<UserResponseDto>> getMe(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(userService.getMe(currentUser), "Profile fetched successfully"));
    }

    @PutMapping("/profile")
    @Operation(summary = "Update profile", description = "Updates the authenticated user's profile information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Profile updated successfully\",\n  \"data\": {\n    \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n    \"fullName\": \"Jane Doe\",\n    \"email\": \"john@example.com\",\n    \"color\": \"#E74C3C\"\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "400", description = "Validation Failed",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Validation Failed\",\n  \"data\": null,\n  \"errors\": [\"fullName: must not be blank\"]\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<UserResponseDto>> updateProfile(@AuthenticationPrincipal User currentUser,
                                                                         @Valid @RequestBody UserUpdateDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(userService.updateProfile(currentUser, dto), "Profile updated successfully"));
    }

    @PatchMapping("/avatar/{avatarId}")
    @Operation(summary = "Update avatar", description = "Updates the authenticated user's profile avatar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avatar updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Avatar updated successfully\",\n  \"data\": {\n    \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n    \"fullName\": \"John Doe\",\n    \"email\": \"john@example.com\"\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Attachment not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Avatar file not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<UserResponseDto>> updateAvatar(@AuthenticationPrincipal User currentUser,
                                                                        @PathVariable UUID avatarId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(userService.updateAvatar(currentUser, avatarId), "Avatar updated successfully"));
    }
}
