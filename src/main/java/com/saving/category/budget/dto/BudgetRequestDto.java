package com.saving.category.budget.dto;

import com.saving.category.budget.domain.entity.Budget;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Getter
@NoArgsConstructor
public class BudgetRequestDto {

    @NotNull(message = "필수 입력값 입니다.")
    @Range(min = 1, message = "1 이상의 값을 입력해주세요")
    private int amount;

    @NotBlank(message = "필수 입력값 입니다.")
    @Pattern(
            message = "YYYY-MM 형식으로 입력해주세요.",
            regexp = "^\\d{4}-\\d{2}$"
    )
    private String budgetYearMonth;


    @Builder
    public BudgetRequestDto(int amount, String budgetYearMonth) {
        this.amount = amount;
        this.budgetYearMonth = budgetYearMonth;
    }

    public Budget toEntity(Long userId, Long categoryId) {
        return Budget.builder()
                .userId(userId)
                .categoryId(categoryId)
                .amount(amount)
                .budgetYearMonth(budgetYearMonth + "-01")
                .build();
    }
}
