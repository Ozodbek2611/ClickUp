package uz.pdp.online.clickup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.entity.relation.CategoryUser;
import uz.pdp.online.clickup.model.categoryUserDto.CategoryUserResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryUserMapper {

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userEmail", source = "user.email")
    CategoryUserResponseDto toResponseDto(CategoryUser categoryUser);

    List<CategoryUserResponseDto> toResponseDtoList(List<CategoryUser> categoryUsers);
}
