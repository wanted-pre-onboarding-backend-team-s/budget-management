package com.saving.category.dto;

import com.saving.category.domain.entity.Category;
import lombok.Getter;

@Getter
public class CategoryListResponseDto {

    private final Long categoryId;
    private final String categoryName;

    public CategoryListResponseDto(Category category) {
        this.categoryId = category.getId();
        this.categoryName = category.getCategoryName();
    }
}
