package com.saving.budget.dto;

import com.saving.budget.domain.entity.Budget;
import lombok.Getter;

@Getter
public class CreatedBudgetResponseDto {

    private final Long budgetId;
    private final Long userId;
    private final Long categoryId;
    private final int amount;
    private final String budgetYearMonth;

    public CreatedBudgetResponseDto(Budget budget) {
        this.budgetId = budget.getId();
        this.userId = budget.getUserId();
        this.categoryId = budget.getCategoryId();
        this.amount = budget.getAmount();
        this.budgetYearMonth = budget.getBudgetYearMonth().substring(0,7);
    }
}
