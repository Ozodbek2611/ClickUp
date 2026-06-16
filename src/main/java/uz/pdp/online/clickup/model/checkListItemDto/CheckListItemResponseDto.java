package uz.pdp.online.clickup.model.checkListItemDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckListItemResponseDto {

    private UUID id;

    private UUID checkListId;

    private Boolean resolved;

    private UUID assignedUserId;

    private String assignedUserEmail;
}
