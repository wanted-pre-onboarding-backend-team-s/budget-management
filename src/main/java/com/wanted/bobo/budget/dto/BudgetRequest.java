package com.wanted.bobo.budget.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wanted.bobo.budget.domain.Budget;
import com.wanted.bobo.category.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetRequest {

    @Min(value = 0, message = "0원 이상 입력해주세요.")
    private int amount;

    @JsonProperty("category_code")
    @NotBlank(message = "카테고리를 선택해주세요.")
    private String category;

    public Budget toEntity(Long userId) {
        return Budget.builder()
                     .userId(userId)
                     .amount(amount)
                     .category(Category.of(category))
                     .build();
    }

}
