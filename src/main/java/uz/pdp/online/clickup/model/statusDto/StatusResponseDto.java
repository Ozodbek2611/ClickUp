package uz.pdp.online.clickup.model.statusDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.online.clickup.entity.enums.Type;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusResponseDto {

    private UUID id;

    private String name;

    private UUID spaceId;

    private UUID projectId;

    private UUID categoryId;

    private String color;

    private Type type;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
