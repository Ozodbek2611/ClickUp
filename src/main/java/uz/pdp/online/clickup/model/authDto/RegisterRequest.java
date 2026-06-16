package uz.pdp.online.clickup.model.authDto;

import lombok.Data;

@Data
public class RegisterRequest {

    String fullName;

    String email;

    String password;
}
