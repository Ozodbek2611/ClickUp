package uz.pdp.online.clickup.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import uz.pdp.online.clickup.common.enums.TaskPermission;

import java.util.UUID;

@Data
@Schema(description = "Category user assignment details")
public class CategoryUserResponseDto {

    @Schema(description = "Unique identifier of the assignment", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "ID of the category", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID categoryId;

    @Schema(description = "ID of the assigned user", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID userId;

    @Schema(description = "Email of the assigned user", example = "john@gmail.com")
    private String userEmail;

    @Schema(description = "Permission level granted", example = "EDIT")
    private TaskPermission taskPermission;
}
