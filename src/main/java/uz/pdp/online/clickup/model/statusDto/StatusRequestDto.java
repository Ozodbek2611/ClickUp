package uz.pdp.online.clickup.model.statusDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.online.clickup.entity.enums.Type;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusRequestDto {

    @NotBlank(message = "Status name cannot be blank")
    private String name;

    private String color;

    @NotNull(message = "Status type is required")
    private Type type;

    private UUID spaceId;

    private UUID projectId;

    private UUID categoryId;
}
