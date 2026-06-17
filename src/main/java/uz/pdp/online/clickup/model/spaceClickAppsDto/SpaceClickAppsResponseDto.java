package uz.pdp.online.clickup.model.spaceClickAppsDto;

import lombok.Data;

import java.util.UUID;

@Data
public class SpaceClickAppsResponseDto {

    private UUID id;

    private UUID spaceId;

    private UUID clickAppsId;

    private String clickAppsName;
}
