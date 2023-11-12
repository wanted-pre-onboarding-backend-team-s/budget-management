package com.wanted.bobo.budget.domain;

import com.wanted.bobo.category.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    boolean existsByUserIdAndCategory(Long userId, Category category);
    List<Budget> findAllByUserId(Long userId);
}
