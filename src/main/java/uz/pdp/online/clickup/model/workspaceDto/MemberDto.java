package uz.pdp.online.clickup.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.online.clickup.entity.enums.TypeOfAction;

import java.sql.Timestamp;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private UUID userId;
    private String fullName;
    private String email;
    private String roleName;
    private Timestamp lastActive;
    private UUID roleId;
    private TypeOfAction typeOfAction;
}
