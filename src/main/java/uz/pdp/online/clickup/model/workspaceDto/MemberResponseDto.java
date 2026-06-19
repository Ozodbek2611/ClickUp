package uz.pdp.online.clickup.model.workspaceDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {

    private UUID userId;

    private String fullName;

    private String email;

    private String roleName;

    private Timestamp lastActive;
}
