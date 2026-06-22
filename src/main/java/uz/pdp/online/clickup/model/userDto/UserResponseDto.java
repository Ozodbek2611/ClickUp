package uz.pdp.online.clickup.model.userDto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.online.clickup.entity.enums.SystemRoleName;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "User profile details returned from the API")
public class UserResponseDto {

    @Schema(description = "Unique identifier of the user", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "Full name of the user", example = "John Doe")
    private String fullName;

    @Schema(description = "Email address of the user", example = "john@gmail.com")
    private String email;

    @Schema(description = "Profile color in hex format", example = "#3498DB")
    private String color;

    @Schema(description = "Initial letter shown as avatar fallback", example = "J")
    private String initialLetter;

    @Schema(description = "ID of the profile picture attachment", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID avatarId;

    @Schema(description = "System-level role: ADMIN or USER", example = "USER")
    private SystemRoleName systemRole;

    @Schema(description = "Timestamp of the user's last activity")
    private Timestamp lastActive;

    @Schema(description = "Whether the user account is active", example = true)
    private boolean enabled;

    @Schema(description = "Timestamp when the account was created")
    private Timestamp createdAt;

    @Schema(description = "Timestamp when the account was last updated")
    private Timestamp updatedAt;
}
