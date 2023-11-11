package com.saving.budget.service;

import com.saving.budget.domain.repository.BudgetRepository;
import com.saving.budget.dto.CreateBudgetRequestDto;
import com.saving.budget.dto.CreatedBudgetResponseDto;
import com.saving.budget.exception.BudgetForCategoryAlreadyExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;

    @Transactional
    public CreatedBudgetResponseDto createBudget(Long userId, CreateBudgetRequestDto createBudgetRequestDto) {

        if (budgetRepository.existsByCategoryIdAndBudgetYearMonth(
                createBudgetRequestDto.getCategoryId(),
                createBudgetRequestDto.getBudgetYearMonth() + "-01")) {
            log.error("[BudgetForCategoryAlreadyExistException] ex");
            throw new BudgetForCategoryAlreadyExistException();
        }
        return new CreatedBudgetResponseDto(
                budgetRepository.save(createBudgetRequestDto.toEntity(userId)));
    }
}
