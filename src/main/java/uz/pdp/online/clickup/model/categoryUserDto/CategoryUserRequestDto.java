package uz.pdp.online.clickup.model.categoryUserDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.pdp.online.clickup.entity.enums.TaskPermission;

import java.util.UUID;

@Data
public class CategoryUserRequestDto {

    @NotNull(message = "Category ID is required")
    private UUID categoryId;

    @NotNull(message = "User ID is required")
    private UUID userId;

    @NotNull(message = "Task permission is required")
    private TaskPermission taskPermission;
}
