package uz.pdp.online.clickup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.entity.relation.SpaceClickApps;
import uz.pdp.online.clickup.model.spaceClickAppsDto.SpaceClickAppsResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpaceClickAppsMapper {

    @Mapping(target = "spaceId", source = "space.id")
    @Mapping(target = "clickAppsId", source = "clickApps.id")
    @Mapping(target = "clickAppsName", source = "clickApps.name")
    SpaceClickAppsResponseDto toResponseDto(SpaceClickApps entity);

    List<SpaceClickAppsResponseDto> toResponseDtoList(List<SpaceClickApps> entities);
}
