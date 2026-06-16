package uz.pdp.online.clickup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.entity.Space;
import uz.pdp.online.clickup.entity.User;
import uz.pdp.online.clickup.entity.enums.TypeOfAction;
import uz.pdp.online.clickup.model.spaceDto.SpaceCreateResponseDto;
import uz.pdp.online.clickup.model.spaceDto.SpaceEditResponseDto;
import uz.pdp.online.clickup.model.spaceDto.SpaceMemberResponseDto;

@Mapper(componentModel = "spring")
public interface SpaceMapper {

    @Mapping(target = "workspaceId", source = "space.workspace.id")
    @Mapping(target = "ownerId", source = "user.id")
    @Mapping(target = "avatarId", source = "space.avatar.id")
    @Mapping(target = "createdAt", expression = "java(new java.sql.Timestamp(System.currentTimeMillis()))")
    @Mapping(target = "id", source = "space.id")
    @Mapping(target = "color", source = "space.color")
    SpaceCreateResponseDto toCreateResponse(Space space, User user);

    @Mapping(target = "avatarId", source = "avatar.id")
    SpaceEditResponseDto toEditResponse(Space space);

    @Mapping(target = "memberId", source = "member.id")
    @Mapping(target = "memberEmail", source = "member.email")
    SpaceMemberResponseDto toMemberResponse(User member, TypeOfAction typeOfAction);
}
