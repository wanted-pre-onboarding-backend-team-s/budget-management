package com.wanted.bobo.budget.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wanted.bobo.budget.domain.Budget;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BudgetResponse {

    private Long id;
    private int amount;

    @JsonProperty("category_code")
    private String category;

    public static BudgetResponse from(Budget budget) {
        return BudgetResponse.builder()
                             .id(budget.getId())
                             .amount(budget.getAmount())
                             .category(budget.getCategory().getCode())
                             .build();
    }
}
