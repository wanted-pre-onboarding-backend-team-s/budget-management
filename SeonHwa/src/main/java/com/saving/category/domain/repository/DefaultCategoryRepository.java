package com.saving.category.domain.repository;

import com.saving.category.domain.entity.DefaultCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefaultCategoryRepository extends JpaRepository<DefaultCategory, Integer> {

    Optional<DefaultCategory> findByDefaultCategoryName(String categoryName);
}
