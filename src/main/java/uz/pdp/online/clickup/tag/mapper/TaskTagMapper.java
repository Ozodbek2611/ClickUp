package uz.pdp.online.clickup.tag.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.tag.entity.TaskTag;
import uz.pdp.online.clickup.tag.dto.TaskTagResponseDto;

@Mapper(componentModel = "spring", uses = {TagMapper.class})
public interface TaskTagMapper {

    @Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "tag", source = "tag")
    TaskTagResponseDto toResponseDto(TaskTag taskTag);
}
