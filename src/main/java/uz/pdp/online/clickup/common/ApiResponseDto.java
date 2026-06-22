package uz.pdp.online.clickup.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(description = "Generic wrapper for API response")
public class ApiResponseDto<T> {

    @Schema(description = "Indicates whether request was successful", example = "true")
    private boolean success;

    @Schema(description = "A brief report on the result", example = "Registration successful. Please verify your email.")
    private String message;

    @Schema(description = "")
    private T data;

    @Schema(description = "List of errors")
    private List<String> errors;

    public static <T> ApiResponseDto<T> ok(T data, String message) {
        return ApiResponseDto.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponseDto<T> error(String message, List<String> errors) {
        return ApiResponseDto.<T>builder()
                .success(false)
                .message(message)
                .errors(errors)
                .build();
    }
}
