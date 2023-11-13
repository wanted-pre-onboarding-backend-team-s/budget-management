package com.wanted.bobo.expense.domain;

import com.wanted.bobo.expense.dto.ExpenseFilter;
import com.wanted.bobo.expense.dto.ExpenseListResponse;

public interface ExpenseRepositoryCustom {
    ExpenseListResponse findByCondition(Long userId, ExpenseFilter condition);
}
