package uz.pdp.online.clickup.icon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.icon.entity.Icon;
import uz.pdp.online.clickup.icon.dto.IconResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IconMapper {

    @Mapping(target = "attachmentId", source = "attachment.id")
    IconResponseDto toResponseDto(Icon icon);

    List<IconResponseDto> toResponseDtoList(List<Icon> icons);
}
