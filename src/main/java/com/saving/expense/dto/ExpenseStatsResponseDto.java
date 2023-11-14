package com.saving.expense.dto;

import lombok.Builder;

/**
 * @param expenseRateComparedLastMonth     지난달 대비 소비율
 * @param expenseRateComparedLastDayOfWeek 지난요일 대비 소비율
 * @param expenseRateComparedOtherUsers    다른 유저 대비 소비율
 */
@Builder
public record ExpenseStatsResponseDto(
        ResultExpenseRateComparedLastMonth expenseRateComparedLastMonth,
        String expenseRateComparedLastDayOfWeek, String expenseRateComparedOtherUsers) {
}
