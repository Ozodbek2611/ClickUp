package uz.pdp.online.clickup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.entity.ProjectUser;
import uz.pdp.online.clickup.model.projectUserDto.ProjectUserResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectUserMapper {

    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userEmail", source = "user.email")
    ProjectUserResponseDto toResponseDto(ProjectUser projectUser);

    List<ProjectUserResponseDto> toResponseDtoList(List<ProjectUser> projectUsers);
}
