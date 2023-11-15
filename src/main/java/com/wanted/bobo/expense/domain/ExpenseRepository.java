package com.wanted.bobo.expense.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExpenseRepository extends
        JpaRepository<Expense, Long>,
        ExpenseRepositoryCustom {

    @Query(value =
            "SELECT * FROM expenses " +
            "WHERE user_id = :userId AND DATE_FORMAT(date, '%Y-%m') = :yearmonth", nativeQuery = true)
    List<Expense> findByUserIdAndYearmonth(Long userId, String yearmonth);

}
