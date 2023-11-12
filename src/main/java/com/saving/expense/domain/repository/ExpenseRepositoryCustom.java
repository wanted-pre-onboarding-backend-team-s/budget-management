package com.saving.expense.domain.repository;

import com.saving.expense.dto.SimpleExpenseDto;
import com.saving.expense.dto.TotalExpenseByCategory;
import java.util.List;

public interface ExpenseRepositoryCustom {

    List<SimpleExpenseDto> listOfTimeBasedExpense(
            Long userId, String startDate, String endDate,
            Long categoryId, Boolean minAmount, Boolean maxAmount);

    Long totalExpense(Long userId, String startDate, String endDate,
            Long categoryId, Boolean minAmount, Boolean maxAmount);

    List<TotalExpenseByCategory> listOfCategoryBasedExpense(Long userId, String startDate,
            String endDate, Long categoryId, Boolean minAmount, Boolean maxAmount);

}
