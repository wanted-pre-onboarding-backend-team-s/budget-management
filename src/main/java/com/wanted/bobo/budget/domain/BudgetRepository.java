package com.wanted.bobo.budget.domain;

import com.wanted.bobo.budget.dto.BudgetStatByCategory;
import com.wanted.bobo.category.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    boolean existsByUserIdAndCategory(Long userId, Category category);

    List<Budget> findAllByUserId(Long userId);

    @Query(value = "SELECT "
            + "category, "
            + "ROUND(SUM(total) / (SELECT COUNT(DISTINCT user_id) FROM budgets)) AS percentage "
            + "FROM ("
            + "SELECT "
            + "category, "
            + "amount / SUM(amount) OVER (PARTITION BY user_id) * 100 AS total "
            + "FROM budgets"
            + ") AS category_percentage GROUP BY category", nativeQuery = true)
    List<BudgetStatByCategory> findAverageBudgetPercentagesByCategory();
}
