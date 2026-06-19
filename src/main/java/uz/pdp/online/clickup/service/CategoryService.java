package uz.pdp.online.clickup.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.online.clickup.entity.domain.Category;
import uz.pdp.online.clickup.entity.domain.Project;
import uz.pdp.online.clickup.exceptions.NotFoundException;
import uz.pdp.online.clickup.mapper.CategoryMapper;
import uz.pdp.online.clickup.model.categoryDto.CategoryRequestDto;
import uz.pdp.online.clickup.model.categoryDto.CategoryResponseDto;
import uz.pdp.online.clickup.model.categoryDto.CategoryUpdateDto;
import uz.pdp.online.clickup.repository.CategoryRepository;
import uz.pdp.online.clickup.repository.ProjectRepository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProjectRepository projectRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryResponseDto create(CategoryRequestDto dto) {
        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new NotFoundException("Project not found with ID: " + dto.getProjectId()));

        Category category = Category.builder()
                .name(dto.getName())
                .color(dto.getColor())
                .accessType(dto.getAccessType() != null ? dto.getAccessType() : "PUBLIC")
                .archived(false)
                .project(project)
                .build();

        Category savedCategory = categoryRepository.save(category);
        log.info("Category successfully created with ID: {}", savedCategory.getId());
        return categoryMapper.toResponseDto(savedCategory);
    }

    @Transactional
    public CategoryResponseDto edit(UUID id, CategoryUpdateDto dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found with ID: " + id));

        category.setName(dto.getName());
        category.setColor(dto.getColor());

        log.info("Category successfully updated with ID: {}", id);
        return categoryMapper.toResponseDto(category);
    }

    @Transactional
    public void delete(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found with ID: " + id));
        categoryRepository.delete(category);
        log.info("Category successfully deleted with ID: {}", id);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getByProjectId(UUID projectId) {
        List<Category> categories = categoryRepository.findAllByProjectId(projectId);
        return categoryMapper.toResponseDtoList(categories);
    }
}
