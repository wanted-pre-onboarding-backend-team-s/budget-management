package com.saving.expense.service;

import com.saving.category.domain.repository.CategoryRepository;
import com.saving.category.exception.CategoryNotFoundException;
import com.saving.expense.domain.entity.Expense;
import com.saving.expense.domain.repository.ExpenseRepository;
import com.saving.expense.dto.ExpenseRequestDto;
import com.saving.expense.dto.CreatedExpenseResponseDto;
import com.saving.expense.exception.ExpenseNotFoundException;
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
    public CreatedExpenseResponseDto createExpense(
            ExpenseRequestDto expenseRequestDto) {

        if (!categoryRepository.existsById(expenseRequestDto.getCategoryId())) {
            throw new CategoryNotFoundException();
        }

        return new CreatedExpenseResponseDto(
                expenseRepository.save(expenseRequestDto.toEntity()));
    }

    @Transactional
    public void updateExpense(Long expenseId, ExpenseRequestDto expenseRequestDto) {

        Expense savedExpense = expenseRepository.findById(expenseId)
                .orElseThrow(ExpenseNotFoundException::new);

        savedExpense.changeExpense(expenseRequestDto);
    }

    @Transactional
    public void deleteExpense(Long expenseId) {

        if (!expenseRepository.existsById(expenseId)) {
            throw new ExpenseNotFoundException();
        }
        expenseRepository.deleteById(expenseId);
    }
}
