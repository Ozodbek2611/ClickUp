package uz.pdp.online.clickup.model.categoryUserDto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.pdp.online.clickup.entity.enums.TaskPermission;

import java.util.UUID;

@Data
@Schema(description = "Request body for assigning a user to a category")
public class CategoryUserRequestDto {

    @NotNull(message = "Category ID is required")
    @Schema(description = "ID of the category", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID categoryId;

    @NotNull(message = "User ID is required")
    @Schema(description = "ID of the user to assign", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID userId;

    @NotNull(message = "Task permission is required")
    @Schema(description = "Permission level: CREATE, EDIT, or VIEW", example = "EDIT")
    private TaskPermission taskPermission;
}
