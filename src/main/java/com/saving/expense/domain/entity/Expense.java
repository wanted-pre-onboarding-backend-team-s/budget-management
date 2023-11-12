package com.saving.expense.domain.entity;

import com.saving.expense.domain.enums.ExpenseMethod;
import com.saving.expense.dto.ExpenseRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "expenses")
@Entity
@Getter
@NoArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long categoryId;
    @Enumerated(value = EnumType.STRING)
    private ExpenseMethod expenseMethod;
    private int amount;
    private String content;
    private int isTotalExpenseApply;
    private String expenseAt;

    @Builder
    public Expense(Long categoryId, String expenseMethod, int amount,
            String content, int isTotalExpenseApply, String expenseAt) {
        this.categoryId = categoryId;
        this.expenseMethod = ExpenseMethod.of(expenseMethod);
        this.amount = amount;
        this.content = content;
        this.isTotalExpenseApply = isTotalExpenseApply;
        this.expenseAt = expenseAt;
    }

    public void changeExpense(ExpenseRequestDto expenseRequestDto) {
        this.categoryId = expenseRequestDto.getCategoryId();
        this.expenseMethod = ExpenseMethod.of(expenseRequestDto.getExpenseMethod());
        this.amount = expenseRequestDto.getAmount();
        this.content = expenseRequestDto.getContent();
        this.isTotalExpenseApply = expenseRequestDto.getIsTotalExpenseApply();
        this.expenseAt = expenseRequestDto.getExpenseAt();
    }
}
