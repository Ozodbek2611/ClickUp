package uz.pdp.online.clickup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.entity.domain.CheckList;
import uz.pdp.online.clickup.model.checkListDto.CheckListResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CheckListMapper {

    @Mapping(target = "taskId", source = "task.id")
    CheckListResponseDto toResponseDto(CheckList checkList);

    List<CheckListResponseDto> toResponseDtoList(List<CheckList> checkLists);
}
