package com.saving.category.budget.domain.repository;

import com.saving.category.budget.domain.entity.Budget;
import com.saving.category.budget.dto.ResultFoundCategoryAndBudgetDto;
import com.saving.category.budget.dto.ResultTotalBudgetByCategoryNameDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    boolean existsByCategoryIdAndBudgetYearMonth(Long categoryId, String budgetYearMonth);

    Optional<Budget> findByIdAndCategoryId(Long id, Long categoryId);

    @Query(value = "select new com.saving.category.budget.dto.ResultFoundCategoryAndBudgetDto ("
            + "b.categoryId, c.categoryName, b.amount)"
            + " from Budget b join Category c"
            + " on b.categoryId = c.id"
            + " where c.userId = :userId"
            + " and b.budgetYearMonth = :thisYearMonth")
    List<ResultFoundCategoryAndBudgetDto> findByCategoryAndBudget(
            @Param("userId") Long userId, @Param("thisYearMonth") String thisYearMonth);

    @Query(value = "select sum(b.amount)"
            + " from Budget b join Category c"
            + " on b.categoryId = c.id"
            + " where c.userId != :userId"
            + " and b.budgetYearMonth = :budgetYearMonth")
    Optional<Long> findSumAmountByNotUserId(
            @Param("userId") Long userId, @Param("budgetYearMonth")String budgetYearMonth);

    @Query(value = "select sum(b.amount)"
            + " from Budget b join Category c"
            + " on b.categoryId = c.id"
            + " where c.userId = :userId"
            + " and b.budgetYearMonth = :budgetYearMonth")
    Optional<Long> findSumAmountByUserId(
            @Param("userId") Long userId, @Param("budgetYearMonth")String budgetYearMonth);

    @Query(value = "select sum(b.amount)"
            + " from Budget b")
    Optional<Long> totalBudget();

    @Query(value = "select new com.saving.category.budget.dto.ResultTotalBudgetByCategoryNameDto("
            + "c.categoryName, sum(b.amount))"
            + " from Budget b join Category c"
            + " on b.categoryId = c.id"
            + " group by c.categoryName")
    List<ResultTotalBudgetByCategoryNameDto> totalBudgetByCategoryName();
}
