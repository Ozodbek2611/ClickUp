package uz.pdp.online.clickup.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(description = "Generic wrapper for API response")
public class ApiResponseDto<T> {

    @Schema(description = "", example = "")
    private boolean success;

    @Schema(description = "A brief report on the result", example = "Email verified successfully")
    private String message;

    @Schema(description = "Main return information")
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
