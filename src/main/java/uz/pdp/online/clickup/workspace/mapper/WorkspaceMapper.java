package uz.pdp.online.clickup.workspace.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.workspace.entity.Workspace;
import uz.pdp.online.clickup.workspace.dto.WorkspaceCreateResponseDto;
import uz.pdp.online.clickup.workspace.dto.WorkspaceEditResponseDto;
import uz.pdp.online.clickup.workspace.dto.WorkspaceGetAllResponseDto;

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
