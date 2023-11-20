package com.saving.expense.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TotalExpenseByCategory {

    @Schema(title = "category id", description = "카테고리 아이디")
    private Long categoryId;

    @Schema(title = "category name", description = "카테고리 이름")
    private String categoryName;

    @Schema(title = "category total expense", description = "해당 카테고리의 총 지출")
    private Integer categoryTotalExpense;

    public TotalExpenseByCategory(Long categoryId, String categoryName, Integer categoryTotalExpense) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryTotalExpense = categoryTotalExpense;
    }
}
