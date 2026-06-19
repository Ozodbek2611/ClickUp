package uz.pdp.online.clickup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.entity.relation.TaskUser;
import uz.pdp.online.clickup.model.taskUserDto.TaskUserResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskUserMapper {

    @Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userEmail", source = "user.email")
    TaskUserResponseDto toResponseDto(TaskUser taskUser);

    List<TaskUserResponseDto> toResponseDtoList(List<TaskUser> taskUsers);
}
