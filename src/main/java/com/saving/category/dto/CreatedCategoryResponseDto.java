package com.saving.category.dto;

import com.saving.category.domain.entity.Category;
import lombok.Getter;

@Getter
public class CreatedCategoryResponseDto {

    private final Long categoryId;
    private final Long userId;
    private final String categoryName;

    public CreatedCategoryResponseDto(Category category) {
        this.categoryId = category.getId();
        this.userId = category.getUserId();
        this.categoryName = category.getCategoryName();
    }
}
