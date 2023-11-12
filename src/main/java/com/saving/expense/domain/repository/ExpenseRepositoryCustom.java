package com.saving.expense.domain.repository;

import com.saving.expense.dto.ExpenseResponseDto;
import com.saving.expense.dto.SimpleExpenseDto;
import com.saving.expense.dto.TotalExpenseByCategory;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepositoryCustom {

    List<SimpleExpenseDto> listOfTimeBasedExpense(
            Long userId, String startDate, String endDate,
            Long categoryId, Boolean minAmount, Boolean maxAmount);

    Long totalExpense(Long userId, String startDate, String endDate,
            Long categoryId, Boolean minAmount, Boolean maxAmount);

    List<TotalExpenseByCategory> listOfCategoryBasedExpense(Long userId, String startDate,
            String endDate, Long categoryId, Boolean minAmount, Boolean maxAmount);

    Optional<ExpenseResponseDto> findByIdAndUserId(Long id, Long userId);


    boolean existsByIdAndUserId(Long expenseId, Long userId);
}
