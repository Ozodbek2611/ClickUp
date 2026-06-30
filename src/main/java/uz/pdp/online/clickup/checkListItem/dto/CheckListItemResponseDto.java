package uz.pdp.online.clickup.checkListItem.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Checklist item details")
public class CheckListItemResponseDto {

    @Schema(description = "Unique identifier of the checklist item", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "ID of the parent checklist", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID checkListId;

    @Schema(description = "Whether the item is completed", example = "false")
    private Boolean resolved;

    @Schema(description = "ID of the assigned user", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID assignedUserId;

    @Schema(description = "Email of the assigned user", example = "john@gmail.com")
    private String assignedUserEmail;
}
