package com.saving.category.budget.domain.repository;

import com.saving.category.budget.domain.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    boolean existsByCategoryIdAndBudgetYearMonth(Long categoryId, String budgetYearMonth);
}
