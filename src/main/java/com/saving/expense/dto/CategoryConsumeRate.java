package com.saving.expense.dto;

import lombok.Builder;

@Builder
public record CategoryConsumeRate(
        Long categoryId,
        String categoryName,
        String consumeRate) {
}
