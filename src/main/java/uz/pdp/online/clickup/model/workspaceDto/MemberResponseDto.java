package uz.pdp.online.clickup.model.workspaceDto;

import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(description = "Workspace member details")
public class MemberResponseDto {

    @Schema(description = "ID of the member", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID userId;

    @Schema(description = "Full name of the member", example = "John Doe")
    private String fullName;

    @Schema(description = "Email of the member", example = "john@gmail.com")
    private String email;

    @Schema(description = "Name of the assigned role", example = "Developer")
    private String roleName;

    @Schema(description = "Timestamp of the member's last activity")
    private Timestamp lastActive;
}
