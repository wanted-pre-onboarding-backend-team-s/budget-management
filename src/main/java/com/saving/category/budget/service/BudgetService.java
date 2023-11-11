package com.saving.category.budget.service;

import com.saving.category.budget.domain.entity.Budget;
import com.saving.category.budget.domain.repository.BudgetRepository;
import com.saving.category.budget.dto.BudgetRequestDto;
import com.saving.category.budget.dto.CreatedBudgetResponseDto;
import com.saving.category.budget.exception.BudgetForCategoryAlreadyExistException;
import com.saving.category.budget.exception.BudgetNotFoundException;
import com.saving.category.domain.repository.CategoryRepository;
import com.saving.category.exception.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BudgetService {

    private final CategoryRepository categoryRepository;
    private final BudgetRepository budgetRepository;

    @Transactional
    public CreatedBudgetResponseDto createBudget(
            Long userId, Long categoryId, BudgetRequestDto budgetRequestDto) {

        if (!categoryRepository.existsByIdAndUserId(categoryId, userId)) {
            throw new CategoryNotFoundException();
        }

        if (budgetRepository.existsByCategoryIdAndBudgetYearMonth(
                categoryId, budgetRequestDto.getBudgetYearMonth() + "-01")) {
            log.error("[BudgetForCategoryAlreadyExistException] ex");
            throw new BudgetForCategoryAlreadyExistException();
        }
        return new CreatedBudgetResponseDto(
                budgetRepository.save(budgetRequestDto.toEntity(userId, categoryId)));
    }

    @Transactional
    public void updateBudget(
            Long userId, Long categoryId, Long budgetId, BudgetRequestDto budgetRequestDto) {

        Budget budget = budgetRepository
                .findByIdAndUserIdAndCategoryId(budgetId, userId, categoryId)
                .orElseThrow(BudgetNotFoundException::new);

        budget.changeAmountAndBudgetYearMonth(budgetRequestDto);
    }
}
