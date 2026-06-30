package uz.pdp.online.clickup.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.user.entity.User;
import uz.pdp.online.clickup.user.dto.UserResponseDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "avatarId", source = "avatar.id")
    UserResponseDto toResponseDto(User user);
}
