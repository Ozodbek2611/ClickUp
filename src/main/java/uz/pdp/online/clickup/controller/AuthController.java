package uz.pdp.online.clickup.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.online.clickup.common.ApiResponseDto;
import uz.pdp.online.clickup.model.authDto.AuthResponseDto;
import uz.pdp.online.clickup.model.authDto.LoginRequest;
import uz.pdp.online.clickup.model.authDto.RegisterRequest;
import uz.pdp.online.clickup.model.authDto.VerifyRequest;
import uz.pdp.online.clickup.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication APIs")
@SecurityRequirements
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(
            summary = "User registration",
            description = "Registers new user and sends verification code"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Registration successful. Please verify your email."),
            @ApiResponse(responseCode = "409", description = "Email already exists")
    })
    public ResponseEntity<ApiResponseDto<Void>> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(ApiResponseDto.ok(null, "Registration successful. Please verify your email."));
    }

    @PostMapping("/login")
    @Operation(
            summary = "User login",
            description = "Check email and enter it into the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Incorrect email or password"),
            @ApiResponse(responseCode = "404", description = "User not found with email")
    })
    public ResponseEntity<ApiResponseDto<AuthResponseDto>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(authService.login(request), "Login successful"));
    }

    @PostMapping("/verify")
    @Operation(
            summary = "Verify user email",
            description = "Check the confirmation code sent to email and activate it if verification is successful"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email verified successfully"),
            @ApiResponse(responseCode = "404", description = "User not found with email"),
            @ApiResponse(responseCode = "401", description = "Invalid verification code")
    })
    public ResponseEntity<ApiResponseDto<Void>> verifyEmail(@RequestBody VerifyRequest verifyRequest) {
        authService.verifyEmail(verifyRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "Email verified successfully"));
    }
}
