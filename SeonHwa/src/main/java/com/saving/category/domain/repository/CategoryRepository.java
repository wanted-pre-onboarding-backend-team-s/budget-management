package com.saving.category.domain.repository;

import com.saving.category.domain.entity.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByUserId(Long userId);

    boolean existsByIdAndUserId(Long id, Long userId);

    Optional<Category> findByUserIdAndCategoryName(Long userId, String categoryName);
}
