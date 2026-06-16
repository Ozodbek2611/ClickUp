package uz.pdp.online.clickup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.entity.TimeTracked;
import uz.pdp.online.clickup.model.timeTrackedDto.TimeTrackedResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TimeTrackedMapper {

    @Mapping(target = "taskId", source = "task.id")
    TimeTrackedResponseDto toResponseDto(TimeTracked timeTracked);

    List<TimeTrackedResponseDto> toResponseDtoList(List<TimeTracked> timeTrackedList);
}
