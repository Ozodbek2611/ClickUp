package uz.pdp.online.clickup.model.categoryUserDto;

import lombok.Data;
import uz.pdp.online.clickup.entity.enums.TaskPermission;

import java.util.UUID;

@Data
public class CategoryUserResponseDto {

    private UUID id;

    private UUID categoryId;

    private UUID userId;

    private String userEmail;

    private TaskPermission taskPermission;
}
