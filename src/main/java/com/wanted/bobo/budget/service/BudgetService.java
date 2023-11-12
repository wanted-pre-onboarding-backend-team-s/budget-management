package com.wanted.bobo.budget.service;

import com.wanted.bobo.budget.domain.BudgetRepository;
import com.wanted.bobo.budget.dto.BudgetRequest;
import com.wanted.bobo.budget.dto.BudgetResponse;
import com.wanted.bobo.budget.exception.DuplicateBudgetCategoryException;
import com.wanted.bobo.category.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;

    @Transactional
    public BudgetResponse makeBudget(Long userId, BudgetRequest request) {
        validateDuplicateCategory(userId, request.getCategory());
        return BudgetResponse.from(budgetRepository.save(request.toEntity(userId)));
    }

    private void validateDuplicateCategory(Long userId, String category) {
        if (budgetRepository.existsByUserIdAndCategory(userId, Category.of(category))) {
            throw new DuplicateBudgetCategoryException();
        }
    }
}
