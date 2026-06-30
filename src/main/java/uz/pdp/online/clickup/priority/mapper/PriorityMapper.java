package uz.pdp.online.clickup.priority.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.priority.entity.Priority;
import uz.pdp.online.clickup.priority.dto.PriorityResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PriorityMapper {

    @Mapping(target = "iconId", source = "icon.id")
    PriorityResponseDto toResponseDto(Priority priority);

    List<PriorityResponseDto> toResponseDtoList(List<Priority> priorities);
}
