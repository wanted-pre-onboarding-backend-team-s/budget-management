package com.saving.category.dto;

import com.saving.category.domain.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CategoryListResponseDto {

    @Schema(title = "category id", description = "카테고리 아이디")
    private final Long categoryId;

    @Schema(title = "category name", description = "카테고리 이름")
    private final String categoryName;

    public CategoryListResponseDto(Category category) {
        this.categoryId = category.getId();
        this.categoryName = category.getCategoryName();
    }
}
