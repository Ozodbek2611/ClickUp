package uz.pdp.online.clickup.model.projectUserDto;

import lombok.Data;
import uz.pdp.online.clickup.entity.enums.TaskPermission;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class ProjectUserResponseDto {

    private UUID id;

    private UUID projectId;

    private UUID userId;

    private String userEmail;

    private TaskPermission taskPermission;

    private Timestamp createdAt;
}
