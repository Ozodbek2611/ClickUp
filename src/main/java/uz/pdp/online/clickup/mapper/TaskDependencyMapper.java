package uz.pdp.online.clickup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.entity.TaskDependency;
import uz.pdp.online.clickup.model.taskDependencyDto.TaskDependencyResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskDependencyMapper {

    @Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "dependencyTaskId", source = "dependencyTask.id")
    TaskDependencyResponseDto toResponseDto(TaskDependency taskDependency);

    List<TaskDependencyResponseDto> toResponseDtoList(List<TaskDependency> taskDependencies);
}
