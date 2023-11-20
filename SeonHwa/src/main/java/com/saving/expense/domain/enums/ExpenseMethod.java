package com.saving.expense.domain.enums;

import com.saving.expense.exception.ExpenseMethodNotFoundException;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExpenseMethod {

    C("C", "카드"),
    M("M", "현금"),
    A("A", "계좌이체");

    private final String value;
    private final String description;

    private static final List<ExpenseMethod> EXPENSE_METHODS =
            Arrays.stream(ExpenseMethod.values()).toList();
    public static ExpenseMethod of(String st) {
        return EXPENSE_METHODS.stream()
                .filter(it -> it.value.equals(st))
                .findFirst()
                .orElseThrow(ExpenseMethodNotFoundException::new);
    }
}
