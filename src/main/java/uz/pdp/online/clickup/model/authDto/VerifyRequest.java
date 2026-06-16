package uz.pdp.online.clickup.model.authDto;

import lombok.Data;

@Data
public class VerifyRequest {

    private String email;

    private String emailCode;
}
