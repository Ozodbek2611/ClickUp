package uz.pdp.online.clickup.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.clickup.entity.relation.CategoryUser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryUserRepository extends JpaRepository<CategoryUser, UUID> {

    Optional<CategoryUser> findByCategoryIdAndUserId(@NotNull(message = "Category ID is required") UUID categoryId,
                                                     @NotNull(message = "User ID is required") UUID userId);

    void deleteByCategoryIdAndUserId(UUID categoryId, UUID userId);

    List<CategoryUser> findAllByCategoryId(UUID categoryId);
}
