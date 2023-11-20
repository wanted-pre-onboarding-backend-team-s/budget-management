package com.wanted.bobo.budget.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BudgetFilter {

    @NotNull(message = "금액을 입력해주세요.")
    private Integer amount;

    @NotBlank(message = "날짜를 입력해주세요.")
    @Pattern(
            regexp = "^[0-9]{4}-(0[1-9]|1[0-2])$",
            message = "날짜 형식에 맞춰 입력해주세요.")
    private String yearmonth;
}
