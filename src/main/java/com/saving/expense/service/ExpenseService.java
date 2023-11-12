package com.saving.expense.service;

import com.saving.category.domain.repository.CategoryRepository;
import com.saving.category.exception.CategoryNotFoundException;
import com.saving.category.exception.MismatchedCategoryIdAndUserIdException;
import com.saving.expense.domain.entity.Expense;
import com.saving.expense.domain.repository.ExpenseRepository;
import com.saving.expense.dto.ExpenseRequestDto;
import com.saving.expense.dto.ExpenseResponseDto;
import com.saving.expense.exception.NotExistExpenseInCategoryException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ExpenseResponseDto createExpense(
            Long userId, Long categoryId, ExpenseRequestDto expenseRequestDto) {

        existUserAndCategory(categoryId, userId);

        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotFoundException();
        }

        return new ExpenseResponseDto(
                expenseRepository.save(expenseRequestDto.toEntity(categoryId)));
    }

    @Transactional
    public void updateExpense(
            Long userId, Long categoryId, Long expenseId, ExpenseRequestDto expenseRequestDto) {

        existUserAndCategory(categoryId, userId);

        Expense savedExpense = expenseRepository.findByIdAndCategoryId(expenseId, categoryId)
                .orElseThrow(NotExistExpenseInCategoryException::new);

        savedExpense.changeExpense(expenseRequestDto);
    }

    @Transactional
    public void deleteExpense(Long userId, Long categoryId, Long expenseId) {

        existUserAndCategory(categoryId, userId);

        if (!expenseRepository.existsByIdAndCategoryId(expenseId, categoryId)) {
            throw new NotExistExpenseInCategoryException();
        }
        expenseRepository.deleteById(expenseId);
    }

    @Transactional(readOnly = true)
    public ExpenseResponseDto getExpense(Long userId, Long categoryId, Long expenseId) {

        existUserAndCategory(categoryId, userId);

        return new ExpenseResponseDto(expenseRepository.findByIdAndCategoryId(expenseId, categoryId)
                .orElseThrow(NotExistExpenseInCategoryException::new));
    }

    private void existUserAndCategory(Long categoryId, Long userId) {
        if (!categoryRepository.existsByIdAndUserId(categoryId, userId)) {
            throw new MismatchedCategoryIdAndUserIdException();
        }
    }
}
