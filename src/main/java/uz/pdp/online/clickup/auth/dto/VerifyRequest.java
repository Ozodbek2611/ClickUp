package uz.pdp.online.clickup.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

@Data
@Schema(description = "Email verification request")
public class VerifyRequest {

    @Schema(description = "Email address to verify", example = "john@gmail.com")
    private String email;

    @Schema(description = "Verification code sent to the email", example = "482910")
    private String emailCode;
}
