package com.wanted.bobo.budget.dto;

import com.wanted.bobo.budget.domain.Budget;
import com.wanted.bobo.category.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BudgetResponse {

    private Long id;
    private int amount;
    private Category category;

    public static BudgetResponse from(Budget budget) {
        return BudgetResponse.builder()
                             .id(budget.getId())
                             .amount(budget.getAmount())
                             .category(budget.getCategory())
                             .build();
    }
}
