package com.saving.expense.dto;

import com.saving.expense.domain.entity.Expense;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(title = "category id", description = "카테고리 아이디")
    @NotNull
    @Range(min = 1, message = "1 이상의 숫자를 입력해야 합니다.")
    private Long categoryId;

    @Schema(title = "expense method", description = "지출 방법")
    @NotBlank(message = "필수 입력값 입니다.")
    @Size(min = 1, max = 1, message = "C, M, A 중 하나를 입력해야합니다.")
    private String expenseMethod;

    @Schema(title = "expense amount", description = "지출 금액")
    @NotNull(message = "필수 입력값 입니다.")
    @Range(min = 1, message = "1 이상의 값을 입력해주세요.")
    private int amount;

    @Schema(title = "expense content", description = "지출 내용")
    @NotBlank(message = "필수 입력값 입니다.")
    private String content;

    @Schema(title = "is total expense apply", description = "지출 합산 여부")
    @NotNull(message = "필수 입력값 입니다.")
    @Range(min = 0, max = 1, message = "지출 합산에 포함하지 않을 경우 0, 지출 합산에 포함할 경우 1을 입력해야합니다.")
    private int isTotalExpenseApply;

    @Schema(title = "expense at", description = "지출 날짜")
    @NotBlank(message = "필수 입력값 입니다.")
    @Pattern(
            message = "YYYY-MM-DD 형식으로 년,월,일을 입력해야합니다.",
            regexp = "^\\d{4}-\\d{2}-\\d{2}$"
    )
    private String expenseAt;

    public Expense toEntity() {
        return Expense.builder()
                .categoryId(categoryId)
                .expenseMethod(expenseMethod)
                .amount(amount)
                .content(content)
                .isTotalExpenseApply(isTotalExpenseApply)
                .expenseAt(expenseAt)
                .build();
    }
}
