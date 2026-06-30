package uz.pdp.online.clickup.category.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.category.entity.Category;
import uz.pdp.online.clickup.category.dto.CategoryResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "createdBy", source = "createdBy.id")
    CategoryResponseDto toResponseDto(Category category);

    List<CategoryResponseDto> toResponseDtoList(List<Category> categories);
}
