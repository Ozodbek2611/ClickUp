package uz.pdp.online.clickup.checkListItem.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request body for adding an item to a checklist")
public class CheckListItemRequestDto {

    @NotBlank(message = "Item name cannot be blank")
    @Schema(description = "Text content of the checklist item", example = "Write unit tests")
    private String name;

    @NotNull(message = "CheckList ID is required")
    @Schema(description = "ID of the parent checklist", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID checkListId;

    @Schema(description = "ID of the user assigned to this item (optional)", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID assignedUserId;

    @Schema(description = "Whether this item is completed", example = "false")
    private Boolean resolved;
}
