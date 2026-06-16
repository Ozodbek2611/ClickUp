package uz.pdp.online.clickup.model.checkListDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckListResponseDto {

    private UUID id;

    private String name;

    private UUID taskId;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
