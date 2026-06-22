package uz.pdp.online.clickup.model.checkListDto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request body for creating a checklist")
public class CheckListRequestDto {

    @NotBlank(message = "Checklist name cannot be blank")
    @Schema(description = "Name of the checklist", example = "Definition of Done")
    private String name;

    @NotNull(message = "Task ID is required")
    @Schema(description = "ID of the task this checklist belongs to", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID taskId;
}
