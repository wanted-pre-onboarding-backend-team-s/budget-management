package com.saving.expense.dto;

import com.saving.expense.domain.entity.Expense;
import lombok.Getter;

@Getter
public class CreatedExpenseResponseDto {

    private final Long expenseId;
    private final Long categoryId;
    private final String expenseMethod;
    private final int amount;
    private final String content;
    private final int isTotalExpenseApply;
    private final String expenseAt;

    public CreatedExpenseResponseDto(Expense expense) {
        this.expenseId = expense.getId();
        this.categoryId = expense.getCategoryId();
        this.expenseMethod = expense.getExpenseMethod().getValue();
        this.amount = expense.getAmount();
        this.content = expense.getContent();
        this.isTotalExpenseApply = expense.getIsTotalExpenseApply();
        this.expenseAt = expense.getExpenseAt();
    }
}
