package com.saving.expense.service;

import com.saving.category.domain.repository.CategoryRepository;
import com.saving.category.exception.CategoryNotFoundException;
import com.saving.expense.domain.repository.ExpenseRepository;
import com.saving.expense.dto.CreateExpenseRequestDto;
import com.saving.expense.dto.CreatedExpenseResponseDto;
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
            CreateExpenseRequestDto createExpenseRequestDto) {

        if (!categoryRepository.existsById(createExpenseRequestDto.getCategoryId())) {
            throw new CategoryNotFoundException();
        }

        return new CreatedExpenseResponseDto(
                expenseRepository.save(createExpenseRequestDto.toEntity()));
    }
}
