package uz.pdp.online.clickup.model.userDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserUpdateDto {

    @NotBlank(message = "Full name cannot be blank")
    private String fullName;

    private String color;
}
