package uz.pdp.online.clickup.checkListItem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.checkListItem.entity.CheckListItem;
import uz.pdp.online.clickup.checkListItem.dto.CheckListItemResponseDto;

@Mapper(componentModel = "spring")
public interface CheckListItemMapper {

    @Mapping(target = "checkListId", source = "checkList.id")
    @Mapping(target = "assignedUserId", source = "assignedUser.id")
    @Mapping(target = "assignedUserEmail", source = "assignedUser.email")
    CheckListItemResponseDto toResponseDto(CheckListItem checkListItem);
}
