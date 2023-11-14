package com.saving.expense.domain.repository;

import com.saving.expense.domain.entity.Expense;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExpenseRepository extends JpaRepository<Expense, Long>, ExpenseRepositoryCustom {

    Optional<Expense> findByIdAndCategoryId(Long id, Long categoryId);

    @Query(value = "select coalesce(sum(e.amount), 0)"
            + " from Expense e"
            + " where e.categoryId = :categoryId"
            + " and e.expenseAt between :startDate and :endDate")
    Optional<Long> findSumOfExpenseByCategoryIdAndTime(@Param("categoryId") Long categoryId,
            @Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query(value = "select coalesce(e.amount, 0)"
            + " from Expense e"
            + " where e.categoryId = :categoryId"
            + " and e.expenseAt = :expenseAt")
    Optional<Integer> findCategoryTodayExpense(@Param("categoryId") Long categoryId,
            @Param("expenseAt") String expenseAt);

    @Query(value = "select coalesce(sum(e.amount), 0)"
            + " from Expense e join Category c"
            + " on e.categoryId = c.id"
            + " where c.userId = :userId"
            + " and e.expenseAt = :expenseAt")
    Optional<Long> todayTotalExpense(
            @Param("userId") Long userId,
            @Param("expenseAt") String expenseAt);

    @Query(value = "select sum(e.amount)"
            + " from Expense e join Category c"
            + " on e.categoryId = c.id"
            + " where c.userId = :userId"
            + " and e.expenseAt between :startDate and :endDate")
    Optional<Long> findSumOfExpenseByUserIdAndTime(@Param("userId") Long userId,
            @Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query(value = "select sum(e.amount)"
            + " from Expense e join Category c"
            + " on e.categoryId = c.id"
            + " where c.userId != :userId"
            + " and e.expenseAt between :startDate and :endDate")
    Optional<Long> findSumAmountByNotUserIdAndTime(
            @Param("userId") Long userId,
            @Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query(value = "select sum(e.amount)"
            + " from Expense e join Category c"
            + " on e.categoryId = c.id"
            + " where c.userId = :userId"
            + " and e.expenseAt between :startDate and :endDate")
    Optional<Long> findSumAmountByUserIdAndTime(
    @Param("userId") Long userId,
    @Param("startDate") String startDate, @Param("endDate") String endDate);

}
