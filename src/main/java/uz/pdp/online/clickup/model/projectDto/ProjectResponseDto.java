package uz.pdp.online.clickup.model.projectDto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class ProjectResponseDto {

    private UUID id;

    private String name;

    private String color;

    private UUID spaceId;

    private Timestamp createdAt;

    private UUID createdBy;
}
