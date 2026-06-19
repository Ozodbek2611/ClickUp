package uz.pdp.online.clickup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.entity.domain.Workspace;
import uz.pdp.online.clickup.model.workspaceDto.WorkspaceCreateResponseDto;
import uz.pdp.online.clickup.model.workspaceDto.WorkspaceEditResponseDto;
import uz.pdp.online.clickup.model.workspaceDto.WorkspaceGetAllResponseDto;

@Mapper(componentModel = "spring")
public interface WorkspaceMapper {

    @Mapping(target = "ownerId", source = "owner.id")
    @Mapping(target = "avatarId", source = "avatar.id")
    WorkspaceCreateResponseDto toCreateResponse(Workspace workspace);

    @Mapping(target = "avatarId", source = "avatar.id")
    WorkspaceEditResponseDto toEditResponse(Workspace workspace);

    @Mapping(target = "ownerId", source = "owner.id")
    @Mapping(target = "avatarId", source = "avatar.id")
    WorkspaceGetAllResponseDto toGetAllResponse(Workspace workspace);
}
