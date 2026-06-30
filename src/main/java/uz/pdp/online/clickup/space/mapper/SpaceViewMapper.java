package uz.pdp.online.clickup.space.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.space.entity.SpaceView;
import uz.pdp.online.clickup.space.dto.SpaceViewResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpaceViewMapper {

    @Mapping(target = "spaceId", source = "space.id")
    @Mapping(target = "viewId", source = "view.id")
    @Mapping(target = "viewName", source = "view.name")
    SpaceViewResponseDto toResponseDto(SpaceView spaceView);

    List<SpaceViewResponseDto> toResponseDtoList(List<SpaceView> spaceViews);
}
