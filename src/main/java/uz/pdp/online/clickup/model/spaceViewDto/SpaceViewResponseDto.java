package uz.pdp.online.clickup.model.spaceViewDto;

import lombok.Data;

import java.util.UUID;

@Data
public class SpaceViewResponseDto {

    private UUID id;

    private Long spaceId;

    private UUID viewId;

    private String viewName;
}
