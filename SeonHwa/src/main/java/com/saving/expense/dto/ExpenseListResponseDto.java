package com.saving.expense.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ExpenseListResponseDto {

    @Schema(title = "total expense", description = "총 지출액")
    private Long totalExpense;

    @Schema(title = "expense list", description = "지출 목록")
    private List<SimpleExpenseDto> expenseList;

    @Schema(title = "total expense by category", description = "카테고리별 총 지출액")
    private List<TotalExpenseByCategory> totalExpenseByCategoryList;

    @Builder
    public ExpenseListResponseDto(Long totalExpense,
            List<TotalExpenseByCategory> totalExpenseByCategoryList,
            List<SimpleExpenseDto> expenseList) {
        this.totalExpense = totalExpense;
        this.totalExpenseByCategoryList = totalExpenseByCategoryList;
        this.expenseList = expenseList;
    }
}
