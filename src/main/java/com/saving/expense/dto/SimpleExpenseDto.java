package com.saving.expense.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimpleExpenseDto {

    @Schema(title = "category id", description = "카테고리 아이디")
    private Long categoryId;

    @Schema(title = "category name", description = "카테고리 이름")
    private String categoryName;

    @Schema(title = "expense amount", description = "지출 금액")
    private Integer amount;

    @Schema(title = "expense at", description = "지출 날짜")
    private String expenseAt;

    public SimpleExpenseDto(Long categoryId, String categoryName, Integer amount,
            String expenseAt) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.amount = amount;
        this.expenseAt = expenseAt;
    }
}
