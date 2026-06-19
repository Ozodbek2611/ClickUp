package uz.pdp.online.clickup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.entity.domain.Task;
import uz.pdp.online.clickup.model.taskDto.TaskResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "statusId", source = "status.id")
    @Mapping(target = "statusName", source = "status.name")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "priorityId", source = "priority.id")
    @Mapping(target = "parentTaskId", source = "parentTask.id")
    TaskResponseDto toResponseDto(Task task);

    List<TaskResponseDto> toResponseDtoList(List<Task> tasks);
}
