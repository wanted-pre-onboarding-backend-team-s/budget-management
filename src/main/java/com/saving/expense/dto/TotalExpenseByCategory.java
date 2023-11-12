package com.saving.expense.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TotalExpenseByCategory {

    private Long categoryId;
    private String categoryName;
    private Integer categoryTotalExpense;

    public TotalExpenseByCategory(Long categoryId, String categoryName, Integer categoryTotalExpense) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryTotalExpense = categoryTotalExpense;
    }
}
