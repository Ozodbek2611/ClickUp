package uz.pdp.online.clickup.model.authDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "User email",
            example = "john@gmail.com")
    private String email;

    @NotBlank(message = "Password is required")
    @Schema(description = "Password",
            example = "123456789")
    private String password;
}
