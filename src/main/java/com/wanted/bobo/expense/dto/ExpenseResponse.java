package com.wanted.bobo.expense.dto;

import com.wanted.bobo.category.Category;
import com.wanted.bobo.expense.domain.Expense;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ExpenseResponse {

    private Long id;
    private int amount;
    private String memo;
    private String date;
    private Category category;

    public static ExpenseResponse from(Expense expense) {
        return ExpenseResponse.builder()
                              .id(expense.getId())
                              .memo(expense.getMemo())
                              .date(expense.getDate().toString())
                              .amount(expense.getAmount())
                              .category(expense.getCategory())
                              .build();
    }
}
