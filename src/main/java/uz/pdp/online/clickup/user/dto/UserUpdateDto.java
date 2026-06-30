package uz.pdp.online.clickup.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Request body for updating user profile")
public class UserUpdateDto {

    @NotBlank(message = "Full name cannot be blank")
    @Schema(description = "New full name for the user", example = "Jane Doe")
    private String fullName;

    @Schema(description = "New profile color in hex format", example = "#E74C3C")
    private String color;
}
