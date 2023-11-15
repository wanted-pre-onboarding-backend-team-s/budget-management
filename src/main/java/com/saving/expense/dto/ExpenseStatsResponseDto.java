package com.saving.expense.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * @param expenseRateComparedLastMonth     지난달 대비 소비율
 * @param expenseRateComparedLastDayOfWeek 지난요일 대비 소비율
 * @param expenseRateComparedOtherUsers    다른 유저 대비 소비율
 */
@Builder
public record ExpenseStatsResponseDto(

        @Schema(title = "expense rate compared last Month", description = "지난 달 대비 소비율")
        ResultExpenseRateComparedLastMonth expenseRateComparedLastMonth,

        @Schema(title = "expense rate compared day of the week", description = "지난 주 대비 소비율")
        String expenseRateComparedLastDayOfWeek,

        @Schema(title = "expense rate compared other users", description = "다른 사용자 대비 소비율")
        String expenseRateComparedOtherUsers) {
}
