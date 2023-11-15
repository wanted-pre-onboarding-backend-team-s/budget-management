package com.wanted.bobo.expense.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExpenseRepository extends
        JpaRepository<Expense, Long>,
        ExpenseRepositoryCustom {

    @Query(value =
            "SELECT * FROM expenses " +
                    "WHERE user_id = :userId AND date >= :startOfMonth AND date < :endDate", nativeQuery = true)
    List<Expense> findByUserIdAndDateRange(Long userId, String startOfMonth, String endDate);
    @Query(value =
            "SELECT * FROM expenses " +
                    "WHERE user_id = :userId AND date = :date", nativeQuery = true)
    List<Expense> findByUserIdAndDate(Long userId, String date);

}
