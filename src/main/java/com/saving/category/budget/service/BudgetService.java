package com.saving.category.budget.service;

import com.saving.category.budget.domain.entity.Budget;
import com.saving.category.budget.domain.repository.BudgetRepository;
import com.saving.category.budget.dto.BudgetRequestDto;
import com.saving.category.budget.dto.CreateBudgetsAutoRequestDto;
import com.saving.category.budget.dto.CreatedBudgetResponseDto;
import com.saving.category.budget.dto.ResultTotalBudgetByCategoryNameDto;
import com.saving.category.budget.exception.BudgetForCategoryAlreadyExistException;
import com.saving.category.budget.exception.BudgetNotFoundException;
import com.saving.category.domain.entity.Category;
import com.saving.category.domain.entity.DefaultCategory;
import com.saving.category.domain.repository.CategoryRepository;
import com.saving.category.domain.repository.DefaultCategoryRepository;
import com.saving.category.exception.MismatchedCategoryIdAndUserIdException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BudgetService {

    private final DefaultCategoryRepository defaultCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final BudgetRepository budgetRepository;

    @Transactional
    public CreatedBudgetResponseDto createBudget(
            Long userId, Long categoryId, BudgetRequestDto budgetRequestDto) {

        existUserAndCategory(categoryId, userId);

        if (budgetRepository.existsByCategoryIdAndBudgetYearMonth(
                categoryId, budgetRequestDto.getBudgetYearMonth() + "-01")) {
            log.error("[BudgetForCategoryAlreadyExistException] ex");
            throw new BudgetForCategoryAlreadyExistException();
        }
        return new CreatedBudgetResponseDto(
                budgetRepository.save(budgetRequestDto.toEntity(categoryId)));
    }

    @Transactional
    public void updateBudget(
            Long userId, Long categoryId, Long budgetId, BudgetRequestDto budgetRequestDto) {

        existUserAndCategory(categoryId, userId);

        Budget budget = budgetRepository
                .findByIdAndCategoryId(budgetId, categoryId)
                .orElseThrow(BudgetNotFoundException::new);

        budget.changeAmountAndBudgetYearMonth(budgetRequestDto);
    }

    @Transactional
    public List<CreatedBudgetResponseDto> createBudgetsAuto(
            Long userId, CreateBudgetsAutoRequestDto createBudgetsAutoRequestDto) {

        int totalAmount = createBudgetsAutoRequestDto.totalAmount();
        String budgetYearMonth = createBudgetsAutoRequestDto.budgetYearMonth() + "-01";

        List<CreatedBudgetResponseDto> list = new ArrayList<>();
        Long totalBudget = budgetRepository.totalBudget().orElse(0L);

        if (totalBudget != 0) {

            List<ResultTotalBudgetByCategoryNameDto> totalBudgetByCategoryNameList =
                    budgetRepository.totalBudgetByCategoryName();

            for (ResultTotalBudgetByCategoryNameDto dto : totalBudgetByCategoryNameList) {

                dto.setAverageBudget(totalBudget);

                Optional<Category> savedCategory =
                        categoryRepository.findByUserIdAndCategoryName(userId, dto.getCategoryName());
                if (savedCategory.isEmpty() || dto.getAverageBudget() == 0) {
                    continue;
                }

                Budget savedBudget = budgetRepository.save(
                        BudgetRequestDto.builder()
                                .amount((int) (totalAmount * dto.getAverageBudget()))
                                .budgetYearMonth(budgetYearMonth)
                                .build()
                                .toEntity(savedCategory.get().getId()));

                list.add(new CreatedBudgetResponseDto(savedBudget));
            }
            return list;
        }

        List<Category> categoryList = categoryRepository.findAllByUserId(userId);
        for (Category category : categoryList) {

            Optional<DefaultCategory> defaultCategory = defaultCategoryRepository
                    .findByDefaultCategoryName(category.getCategoryName());

            if (defaultCategory.isEmpty()) {
                continue;
            }

            Budget savedBudget = budgetRepository.save(
                    BudgetRequestDto.builder()
                            .amount((int) (totalAmount * defaultCategory.get().getDefaultBudget()))
                            .budgetYearMonth(budgetYearMonth)
                            .build()
                            .toEntity(category.getId()));

            list.add(new CreatedBudgetResponseDto(savedBudget));
        }
        return list;
    }

    private void existUserAndCategory(Long categoryId, Long userId) {
        if (!categoryRepository.existsByIdAndUserId(categoryId, userId)) {
            throw new MismatchedCategoryIdAndUserIdException();
        }
    }
}
