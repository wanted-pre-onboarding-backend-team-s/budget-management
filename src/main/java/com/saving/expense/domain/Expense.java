package com.saving.expense.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
    @GeneratedValue
    private Long id;
    private Long userId;
    private String categoryName;
    private char expenseMethod;
    private int amount;
    private String content;
    private int isTotalExpenseApply;
    private String expenseAt;

    @Builder
    public Expense(Long userId, String categoryName, char expenseMethod, int amount,
            String content, int isTotalExpenseApply, String expenseAt) {
        this.userId = userId;
        this.categoryName = categoryName;
        this.expenseMethod = expenseMethod;
        this.amount = amount;
        this.content = content;
        this.isTotalExpenseApply = isTotalExpenseApply;
        this.expenseAt = expenseAt;
    }
}
