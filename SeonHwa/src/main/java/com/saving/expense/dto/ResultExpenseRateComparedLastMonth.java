package com.saving.expense.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Builder
public record ResultExpenseRateComparedLastMonth(

        @Schema(title = "total expense rate compared last month", description = "지난달 대비 총 소비율")
        String totalExpenseRateComparedLastMonth,

        @Schema(title = "expense rate categories compared last month", description = "카테고리별 지난달 대비 총 소비율")
        List<CategoryConsumeRate> expenseRateCategoriesComparedLastMonth) {

}
