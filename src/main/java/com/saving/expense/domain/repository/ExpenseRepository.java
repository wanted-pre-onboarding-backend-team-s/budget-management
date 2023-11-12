package com.saving.expense.domain.repository;

import com.saving.expense.domain.entity.Expense;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long>, ExpenseRepositoryCustom {

    Optional<Expense> findByIdAndCategoryId(Long id, Long categoryId);

    boolean existsByIdAndCategoryId(Long id, Long categoryId);
}
