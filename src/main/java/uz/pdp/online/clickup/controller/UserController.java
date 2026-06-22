package uz.pdp.online.clickup.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.entity.domain.User;
import uz.pdp.online.clickup.common.ApiResponseDto;
import uz.pdp.online.clickup.model.userDto.UserResponseDto;
import uz.pdp.online.clickup.model.userDto.UserUpdateDto;
import uz.pdp.online.clickup.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "User profile management APIs")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Get current user", description = "Returns profile information of the authenticated user")
    @ApiResponse(responseCode = "200", description = "Profile fetched successfully")
    public ResponseEntity<ApiResponseDto<UserResponseDto>> getMe(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(userService.getMe(currentUser), "Profile fetched successfully"));
    }

    @PutMapping("/profile")
    @Operation(summary = "Update profile", description = "Updates the authenticated user's profile information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profile updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    public ResponseEntity<ApiResponseDto<UserResponseDto>> updateProfile(@AuthenticationPrincipal User currentUser,
                                                                         @Valid @RequestBody UserUpdateDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(userService.updateProfile(currentUser, dto), "Profile updated successfully"));
    }

    @PatchMapping("/avatar/{avatarId}")
    @Operation(summary = "Update avatar", description = "Updates the authenticated user's profile avatar")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Avatar updated successfully"),
            @ApiResponse(responseCode = "404", description = "Attachment not found")
    })
    public ResponseEntity<ApiResponseDto<UserResponseDto>> updateAvatar(@AuthenticationPrincipal User currentUser,
                                                                        @PathVariable UUID avatarId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(userService.updateAvatar(currentUser, avatarId), "Avatar updated successfully"));
    }
}
