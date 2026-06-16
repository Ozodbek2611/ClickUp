package uz.pdp.online.clickup.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.entity.User;
import uz.pdp.online.clickup.model.ApiResponse;
import uz.pdp.online.clickup.model.userDto.UserResponseDto;
import uz.pdp.online.clickup.model.userDto.UserUpdateDto;
import uz.pdp.online.clickup.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponseDto>> getMe(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(userService.getMe(currentUser), "Profile fetched successfully"));
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<UserResponseDto>> updateProfile(@AuthenticationPrincipal User currentUser,
                                                                      @Valid @RequestBody UserUpdateDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(userService.updateProfile(currentUser, dto), "Profile updated successfully"));
    }

    @PatchMapping("/avatar/{avatarId}")
    public ResponseEntity<ApiResponse<UserResponseDto>> updateAvatar(@AuthenticationPrincipal User currentUser,
                                                                     @PathVariable UUID avatarId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(userService.updateAvatar(currentUser, avatarId), "Avatar updated successfully"));
    }
}
