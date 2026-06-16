package uz.pdp.online.clickup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.entity.Icon;
import uz.pdp.online.clickup.model.iconDto.IconResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IconMapper {

    @Mapping(target = "attachmentId", source = "attachment.id")
    IconResponseDto toResponseDto(Icon icon);

    List<IconResponseDto> toResponseDtoList(List<Icon> icons);
}
