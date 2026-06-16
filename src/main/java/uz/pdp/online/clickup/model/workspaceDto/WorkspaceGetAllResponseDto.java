package uz.pdp.online.clickup.model.workspaceDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceGetAllResponseDto {

    private Long id;

    private String name;

    private String color;

    private String initialLetter;

    private UUID ownerId;

    private UUID avatarId;
}
