package com.wanted.bobo.budget.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wanted.bobo.budget.domain.Budget;
import com.wanted.bobo.category.Category;
import java.time.YearMonth;
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

    @JsonProperty("year_month")
    private YearMonth yearmonth;

    public static BudgetResponse from(Budget budget) {
        return BudgetResponse.builder()
                             .id(budget.getId())
                             .yearmonth(budget.getYearmonth())
                             .amount(budget.getAmount())
                             .category(budget.getCategory())
                             .build();
    }
}
