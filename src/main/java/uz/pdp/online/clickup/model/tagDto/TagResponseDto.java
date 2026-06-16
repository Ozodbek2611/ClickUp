package uz.pdp.online.clickup.model.tagDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagResponseDto {

    private UUID id;

    private String name;

    private String color;

    private Long workspaceId;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
