package uz.pdp.online.clickup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.entity.CheckListItem;
import uz.pdp.online.clickup.model.checkListItemDto.CheckListItemResponseDto;

@Mapper(componentModel = "spring")
public interface CheckListItemMapper {

    @Mapping(target = "checkListId", source = "checkList.id")
    @Mapping(target = "assignedUserId", source = "assignedUser.id")
    @Mapping(target = "assignedUserEmail", source = "assignedUser.email")
    CheckListItemResponseDto toResponseDto(CheckListItem checkListItem);
}
