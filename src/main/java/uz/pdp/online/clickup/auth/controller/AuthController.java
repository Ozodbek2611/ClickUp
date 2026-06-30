package uz.pdp.online.clickup.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
import uz.pdp.online.clickup.audit.dto.AuthResponseDto;
import uz.pdp.online.clickup.audit.dto.LoginRequest;
import uz.pdp.online.clickup.audit.dto.RegisterRequest;
import uz.pdp.online.clickup.audit.dto.VerifyRequest;
import uz.pdp.online.clickup.auth.service.AuthService;

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
            @ApiResponse(responseCode = "202", description = "Registration successful. Please verify your email.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Registration successful. Please verify your email.\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "400", description = "Validation Failed",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Validation Failed\",\n  \"data\": null,\n  \"errors\": [\"email: must be a well-formed email address\"]\n}")
                    )),
            @ApiResponse(responseCode = "409", description = "Email already exists",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Email already exists: user@example.com\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
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
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Login successful\",\n  \"data\": {\n    \"token\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIn0.signature\"\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "400", description = "Validation Failed",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Validation Failed\",\n  \"data\": null,\n  \"errors\": [\"password: must not be blank\"]\n}")
                    )),
            @ApiResponse(responseCode = "401", description = "Incorrect email or password",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Incorrect email or password\",\n  \"data\": null,\n  \"errors\": [\"Bad credentials\"]\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "User not found with email",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"User not found with email: user@example.com\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
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
    )    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email verified successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Email verified successfully\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "403", description = "Invalid verification code",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Invalid verification code\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "User not found with email",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"User not found with email: user@example.com\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<Void>> verifyEmail(@RequestBody VerifyRequest verifyRequest) {
        authService.verifyEmail(verifyRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "Email verified successfully"));
    }
}
