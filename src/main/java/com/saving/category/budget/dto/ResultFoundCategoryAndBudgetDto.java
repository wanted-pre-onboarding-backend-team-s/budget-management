package com.saving.category.budget.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResultFoundCategoryAndBudgetDto {

    private Long categoryId;
    private String categoryName;
    private int amount;

    @Builder
    public ResultFoundCategoryAndBudgetDto(Long categoryId, String categoryName, int amount) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.amount = amount;
    }
}
