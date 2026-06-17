package uz.pdp.online.clickup.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.online.clickup.entity.Category;
import uz.pdp.online.clickup.entity.CategoryUser;
import uz.pdp.online.clickup.entity.User;
import uz.pdp.online.clickup.entity.enums.TaskPermission;
import uz.pdp.online.clickup.exceptions.AlreadyExistsException;
import uz.pdp.online.clickup.exceptions.NotFoundException;
import uz.pdp.online.clickup.mapper.CategoryUserMapper;
import uz.pdp.online.clickup.model.categoryUserDto.CategoryUserRequestDto;
import uz.pdp.online.clickup.model.categoryUserDto.CategoryUserResponseDto;
import uz.pdp.online.clickup.repository.CategoryRepository;
import uz.pdp.online.clickup.repository.CategoryUserRepository;
import uz.pdp.online.clickup.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryUserService {

    private final CategoryUserRepository categoryUserRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CategoryUserMapper categoryUserMapper;

    @Transactional
    public CategoryUserResponseDto assign(CategoryUserRequestDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found with ID: " + dto.getCategoryId()));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + dto.getUserId()));

        if (categoryUserRepository.findByCategoryIdAndUserId(dto.getCategoryId(), dto.getUserId()).isPresent())
            throw new AlreadyExistsException("User already assigned to category");

        CategoryUser categoryUser = new CategoryUser();
        categoryUser.setCategory(category);
        categoryUser.setUser(user);
        categoryUser.setTaskPermission(dto.getTaskPermission());

        CategoryUser saved = categoryUserRepository.save(categoryUser);

        log.info("User assigned to category. Category ID: {}, User ID: {}", dto.getCategoryId(), dto.getUserId());

        return categoryUserMapper.toResponseDto(saved);
    }

    @Transactional
    public void updatePermission(UUID categoryId, UUID userId, TaskPermission taskPermission) {
        CategoryUser categoryUser = categoryUserRepository.findByCategoryIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new NotFoundException("Category not found with ID: " + categoryId));

        categoryUser.setTaskPermission(taskPermission);

        log.info("Category permission updated. Category ID: {}, User ID: {}", categoryId, userId);
    }

    @Transactional
    public void unassign(UUID categoryId, UUID userId) {
        categoryUserRepository.findByCategoryIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new NotFoundException("User not assigned to category"));

        categoryUserRepository.deleteByCategoryIdAndUserId(categoryId, userId);

        log.info("User removed from category. Category ID: {}, User ID: {}", categoryId, userId);
    }

    @Transactional(readOnly = true)
    public List<CategoryUserResponseDto> getByCategoryId(UUID categoryId) {
        return categoryUserMapper.toResponseDtoList(categoryUserRepository.findAllByCategoryId(categoryId));
    }
}
