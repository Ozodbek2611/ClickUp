package uz.pdp.online.clickup.model.authDto;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;

    private String password;
}
