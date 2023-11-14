package com.saving.expense.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record ResultExpenseRateComparedLastMonth(
        String totalExpenseRateComparedLastMonth,
        List<CategoryConsumeRate> expenseRateCategoriesComparedLastMonth) {

}
