package uz.pdp.online.clickup.model.checkListDto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Checklist details returned from the API")
public class CheckListResponseDto {

    @Schema(description = "Unique identifier of the checklist", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "Name of the checklist", example = "Definition of Done")
    private String name;

    @Schema(description = "ID of the parent task", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID taskId;

    @Schema(description = "Timestamp when the checklist was created")
    private Timestamp createdAt;

    @Schema(description = "Timestamp when the checklist was last updated")
    private Timestamp updatedAt;
}
