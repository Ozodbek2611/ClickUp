package uz.pdp.online.clickup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.entity.domain.Tag;
import uz.pdp.online.clickup.model.tagDto.TagResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

    @Mapping(target = "workspaceId", source = "workspace.id")
    TagResponseDto toResponseDto(Tag tag);

    List<TagResponseDto> toResponseDtoList(List<Tag> tags);
}
