package com.wanted.bobo.budget.dto;

import com.wanted.bobo.budget.domain.Budget;
import com.wanted.bobo.category.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetRequest {

    @Min(value = 0, message = "0원 이상 입력해주세요.")
    private int amount;

    @NotBlank(message = "카테고리를 선택해주세요.")
    private String category;

    @Pattern(
            regexp = "^[0-9]{4}-(0[1-9]|1[0-2])$",
            message = "날짜 형식에 맞춰 입력해주세요.")
    private String yearmonth;

    public Budget toEntity(Long userId) {
        return Budget.builder()
                     .userId(userId)
                     .amount(amount)
                     .category(Category.of(category))
                     .yearMonth(YearMonth.parse(yearmonth, DateTimeFormatter.ofPattern("yyyy-MM")))
                     .build();
    }

}
