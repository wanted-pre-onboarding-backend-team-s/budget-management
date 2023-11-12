package com.saving.expense.dto;

import com.saving.expense.domain.entity.Expense;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Getter
@NoArgsConstructor
public class ExpenseRequestDto {

    @NotBlank(message = "필수 입력값 입니다.")
    @Size(min = 1, max = 1, message = "C, M, A 중 하나를 입력해야합니다.")
    private String expenseMethod;

    @NotNull(message = "필수 입력값 입니다.")
    @Range(min = 1, message = "1 이상의 값을 입력해주세요.")
    private int amount;

    @NotBlank(message = "필수 입력값 입니다.")
    private String content;

    @NotNull(message = "필수 입력값 입니다.")
    @Range(min = 0, max = 1, message = "0 또는 1을 입력해야합니다.")
    private int isTotalExpenseApply;

    @NotBlank(message = "필수 입력값 입니다.")
    @Pattern(
            message = "YYYY-MM-DD 형식으로 년,월,일을 입력해야합니다.",
            regexp = "^\\d{4}-\\d{2}-\\d{2}$"
    )
    private String expenseAt;

    public Expense toEntity() {
        return Expense.builder()
                .expenseMethod(expenseMethod)
                .amount(amount)
                .content(content)
                .isTotalExpenseApply(isTotalExpenseApply)
                .expenseAt(expenseAt)
                .build();
    }
}
