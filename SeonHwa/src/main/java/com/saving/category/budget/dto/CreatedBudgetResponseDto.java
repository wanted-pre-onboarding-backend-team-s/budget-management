package com.saving.category.budget.dto;

import com.saving.category.budget.domain.entity.Budget;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CreatedBudgetResponseDto {

    @Schema(title = "budget id", description = "예산 아이디")
    private final Long budgetId;

    @Schema(title = "category id", description = "카테고리 아이디")
    private final Long categoryId;

    @Schema(title = "budget amount", description = "예산 금액")
    private final int amount;

    @Schema(title = "budget year,month", description = "예산 년,월")
    private final String budgetYearMonth;

    public CreatedBudgetResponseDto(Budget budget) {
        this.budgetId = budget.getId();
        this.categoryId = budget.getCategoryId();
        this.amount = budget.getAmount();
        this.budgetYearMonth = budget.getBudgetYearMonth().substring(0,7);
    }
}
