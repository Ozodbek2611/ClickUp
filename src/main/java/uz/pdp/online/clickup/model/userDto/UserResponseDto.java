package uz.pdp.online.clickup.model.userDto;

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
public class UserResponseDto {

    private UUID id;

    private String fullName;

    private String email;

    private String color;

    private String initialLetter;

    private UUID avatarId;

    private SystemRoleName systemRole;

    private Timestamp lastActive;

    private boolean enabled;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
