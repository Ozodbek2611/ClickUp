package uz.pdp.online.clickup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.entity.User;
import uz.pdp.online.clickup.model.userDto.UserResponseDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "avatarId", source = "avatar.id")
    UserResponseDto toResponseDto(User user);
}
