package com.saving.expense.dto;

import com.saving.expense.domain.entity.Expense;
import com.saving.expense.domain.enums.ExpenseMethod;
import lombok.Getter;

@Getter
public class ExpenseResponseDto {

    private final Long expenseId;
    private final Long categoryId;
    private final String expenseMethod;
    private final int amount;
    private final String content;
    private final int isTotalExpenseApply;
    private final String expenseAt;

    public ExpenseResponseDto(Long expenseId, Long categoryId, ExpenseMethod expenseMethod, int amount,
            String content, int isTotalExpenseApply, String expenseAt) {
        this.expenseId = expenseId;
        this.categoryId = categoryId;
        this.expenseMethod = expenseMethod.getValue();
        this.amount = amount;
        this.content = content;
        this.isTotalExpenseApply = isTotalExpenseApply;
        this.expenseAt = expenseAt;
    }

    public ExpenseResponseDto(Expense expense) {
        this.expenseId = expense.getId();
        this.categoryId = expense.getCategoryId();
        this.expenseMethod = expense.getExpenseMethod().getValue();
        this.amount = expense.getAmount();
        this.content = expense.getContent();
        this.isTotalExpenseApply = expense.getIsTotalExpenseApply();
        this.expenseAt = expense.getExpenseAt();
    }
}
