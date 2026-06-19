package uz.pdp.online.clickup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.entity.relation.TaskTag;
import uz.pdp.online.clickup.model.taskTagDto.TaskTagResponseDto;

@Mapper(componentModel = "spring", uses = {TagMapper.class})
public interface TaskTagMapper {

    @Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "tag", source = "tag")
    TaskTagResponseDto toResponseDto(TaskTag taskTag);
}
