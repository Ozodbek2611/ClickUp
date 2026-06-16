package uz.pdp.online.clickup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.entity.Status;
import uz.pdp.online.clickup.model.statusDto.StatusResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StatusMapper {

    @Mapping(target = "spaceId", source = "space.id")
    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "categoryId", source = "category.id")
    StatusResponseDto toResponseDto(Status status);

    List<StatusResponseDto> toResponseDtoList(List<Status> statuses);
}
