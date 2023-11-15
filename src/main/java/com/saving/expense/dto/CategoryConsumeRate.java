package com.saving.expense.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record CategoryConsumeRate(

        @Schema(title = "category id", description = "카테고리 아이디")
        Long categoryId,
        @Schema(title = "category name", description = "카테고리 이름")
        String categoryName,
        @Schema(title = "category expense rate", description = "해당 카테고리 소비율")
        String expenseRate) {
}
