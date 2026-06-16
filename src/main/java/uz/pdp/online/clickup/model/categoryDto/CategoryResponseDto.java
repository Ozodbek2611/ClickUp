package uz.pdp.online.clickup.model.categoryDto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class CategoryResponseDto {

    private UUID id;

    private String name;

    private String color;

    private String accessType;

    private Boolean archived;

    private UUID projectId;

    private Timestamp createdAt;

    private UUID createdBy;
}
