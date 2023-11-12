package com.saving.expense.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ExpenseListResponseDto {

    private Long totalExpense;
    private List<SimpleExpenseDto> expenseList;
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
