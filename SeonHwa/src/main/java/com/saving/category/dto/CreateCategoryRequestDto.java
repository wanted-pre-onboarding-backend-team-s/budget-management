package com.saving.category.dto;

import com.saving.category.domain.entity.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateCategoryRequestDto {

    private final Long userId;
    private final String categoryName;

    @Builder
    public CreateCategoryRequestDto(Long userId, String categoryName) {
        this.userId = userId;
        this.categoryName = categoryName;
    }

    public Category toEntity() {
        return Category.builder()
                .userId(userId)
                .categoryName(categoryName)
                .build();
    }
}
