package com.saving.category.domain.repository;

import com.saving.category.domain.entity.DefaultCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefaultCategoryRepository extends JpaRepository<DefaultCategory, Integer> {

}
