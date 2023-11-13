package com.wanted.bobo.expense.domain;

import com.wanted.bobo.expense.dto.ExpenseFilter;
import com.wanted.bobo.expense.dto.ExpenseResponse;
import java.util.List;

public interface ExpenseRepositoryCustom {
    List<ExpenseResponse> findByCondition(Long userId, ExpenseFilter condition);
}
