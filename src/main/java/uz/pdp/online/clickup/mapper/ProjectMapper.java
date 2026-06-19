package uz.pdp.online.clickup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.entity.domain.Project;
import uz.pdp.online.clickup.model.projectDto.ProjectResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(target = "spaceId", source = "space.id")
    @Mapping(target = "createdBy", source = "createdBy.id")
    ProjectResponseDto toResponseDto(Project project);

    List<ProjectResponseDto> toResponseDtoList(List<Project> projects);
}
