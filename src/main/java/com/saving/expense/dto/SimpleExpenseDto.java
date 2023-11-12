package com.saving.expense.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimpleExpenseDto {

    private Long categoryId;
    private String categoryName;
    private Integer amount;
    private String expenseAt;

    public SimpleExpenseDto(Long categoryId, String categoryName, Integer amount,
            String expenseAt) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.amount = amount;
        this.expenseAt = expenseAt;
    }
}
