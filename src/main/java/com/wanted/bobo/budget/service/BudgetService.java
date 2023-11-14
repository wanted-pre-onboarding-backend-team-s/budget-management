package com.wanted.bobo.budget.service;

import com.wanted.bobo.budget.domain.Budget;
import com.wanted.bobo.budget.domain.BudgetRepository;
import com.wanted.bobo.budget.dto.BudgetRequest;
import com.wanted.bobo.budget.dto.BudgetResponse;
import com.wanted.bobo.budget.exception.DuplicateBudgetCategoryException;
import com.wanted.bobo.budget.exception.NotFoundBudgetException;
import com.wanted.bobo.category.Category;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;

    @Transactional(readOnly = true)
    public List<BudgetResponse> getBudgets(Long userId) {
        return budgetRepository.findAllByUserId(userId)
                               .stream()
                               .map(BudgetResponse::from)
                               .toList();
    }

    @Transactional
    public BudgetResponse setBudget(Long userId, BudgetRequest request) {
        validateDuplicateCategory(
                userId,
                request.getCategory(),
                YearMonth.parse(request.getYearmonth(), DateTimeFormatter.ofPattern("yyyy-MM")));

        return BudgetResponse.from(budgetRepository.save(request.toEntity(userId)));
    }

    @Transactional
    public BudgetResponse modifyBudget(Long userId, Long budgetId, BudgetRequest request) {
        Budget budget = findBudget(budgetId);
        budget.verifyMatchUser(userId);

        validateCategory(userId, budget, request);
        budget.changeInfo(request);

        return BudgetResponse.from(budget);
    }

    @Transactional
    public void removeBudget(Long userId, Long budgetId) {
        Budget budget = findBudget(budgetId);
        budget.verifyMatchUser(userId);
        budgetRepository.delete(budget);
    }

    private void validateCategory(Long userId, Budget budget, BudgetRequest request) {
        String category = request.getCategory();
        if (!budget.verifyEqualCategory(Category.of(category))) {
            validateDuplicateCategory(
                    userId,
                    category,
                    YearMonth.parse(request.getYearmonth(), DateTimeFormatter.ofPattern("yyyy-MM")));
        }
    }

    private void validateDuplicateCategory(Long userId, String category, YearMonth yearmonth) {
        if (budgetRepository.existsByUserIdAndCategoryAndYearmonth(
                userId, Category.of(category), yearmonth)) {
            throw new DuplicateBudgetCategoryException();
        }
    }

    private Budget findBudget(Long id) {
        return budgetRepository.findById(id).orElseThrow(NotFoundBudgetException::new);
    }
}
