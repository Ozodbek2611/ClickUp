package uz.pdp.online.clickup.model.spaceViewDto;

import lombok.Data;

import java.util.UUID;

@Data
public class SpaceViewResponseDto {

    private UUID id;

    private UUID spaceId;

    private UUID viewId;

    private String viewName;
}
