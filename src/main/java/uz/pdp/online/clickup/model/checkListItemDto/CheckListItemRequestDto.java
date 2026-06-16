package uz.pdp.online.clickup.model.checkListItemDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckListItemRequestDto {

    @NotBlank(message = "Item name cannot be blank")
    private String name;

    @NotNull(message = "CheckList ID is required")
    private UUID checkListId;

    private UUID assignedUserId;

    private Boolean resolved;
}
