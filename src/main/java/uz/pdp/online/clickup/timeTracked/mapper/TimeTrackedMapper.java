package uz.pdp.online.clickup.timeTracked.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.timeTracked.entity.TimeTracked;
import uz.pdp.online.clickup.timeTracked.dto.TimeTrackedResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TimeTrackedMapper {

    @Mapping(target = "taskId", source = "task.id")
    TimeTrackedResponseDto toResponseDto(TimeTracked timeTracked);

    List<TimeTrackedResponseDto> toResponseDtoList(List<TimeTracked> timeTrackedList);
}
