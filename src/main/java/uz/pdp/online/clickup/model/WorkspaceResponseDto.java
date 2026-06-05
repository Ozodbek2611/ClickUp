package uz.pdp.online.clickup.model;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Builder
public class WorkspaceResponseDto {
    private Long id;
    private String name;
    private String color;
    private String initialLetter;
    private UUID ownerId;
    private UUID avatarId;
    private UUID createdById;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}