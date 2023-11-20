package com.saving.category.budget.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;

public record CreateBudgetsAutoRequestDto(

        @Schema(title = "total amount", description = "자동 예산 설정을 받을 전체 금액")
        @NotNull(message = "필수 입력값 입니다.")
        @Range(min = 100000, message = "10만원 이상의 값을 입력해주세요.")
        int totalAmount,

        @Schema(title = "budget year,month", description = "예산 년,월")
        @NotBlank(message = "필수 입력값 입니다.")
        @Pattern(message = "YYYY-MM 형식으로 입력해주세요.", regexp = "^\\d{4}-\\d{2}$")
        String budgetYearMonth
) {}
