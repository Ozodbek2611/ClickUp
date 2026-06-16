package uz.pdp.online.clickup.model.taskUserDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskUserResponseDto {

    private UUID id;

    private UUID taskId;

    private UUID userId;

    private String userEmail;

    private Timestamp createdAt;
}
