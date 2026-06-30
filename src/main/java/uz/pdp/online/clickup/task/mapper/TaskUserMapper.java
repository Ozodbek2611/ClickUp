package uz.pdp.online.clickup.task.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.task.entity.TaskUser;
import uz.pdp.online.clickup.task.dto.TaskUserResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskUserMapper {

    @Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userEmail", source = "user.email")
    TaskUserResponseDto toResponseDto(TaskUser taskUser);

    List<TaskUserResponseDto> toResponseDtoList(List<TaskUser> taskUsers);
}
