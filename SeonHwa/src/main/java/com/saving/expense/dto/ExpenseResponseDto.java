package com.saving.expense.dto;

import com.saving.expense.domain.entity.Expense;
import com.saving.expense.domain.enums.ExpenseMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ExpenseResponseDto {

    @Schema(title = "expense id", description = "지출 아이디")
    private final Long expenseId;

    @Schema(title = "category id", description = "카테고리 아이디")
    private final Long categoryId;

    @Schema(title = "expense method", description = "지출 방법")
    private final String expenseMethod;

    @Schema(title = "expense amount", description = "지출 금액")
    private final int amount;

    @Schema(title = "expense content", description = "지출 내용")
    private final String content;

    @Schema(title = "is total expense apply", description = "지출 합산 여부")
    private final int isTotalExpenseApply;

    @Schema(title = "expense at", description = "지출 날짜")
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
