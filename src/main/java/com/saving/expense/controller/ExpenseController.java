package com.saving.expense.controller;

import com.saving.common.response.ApiResponse;
import com.saving.expense.dto.ExpenseRequestDto;
import com.saving.expense.dto.CreatedExpenseResponseDto;
import com.saving.expense.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreatedExpenseResponseDto> createExpense(
            @RequestAttribute Long userId,
            @Valid @RequestBody ExpenseRequestDto expenseRequestDto) {

        return ApiResponse.created(expenseService.createExpense(userId, expenseRequestDto));
    }

    @PutMapping("/{expenseId}")
    public ApiResponse<String> updateExpense(
            @RequestAttribute Long userId,
            @PathVariable Long expenseId,
            @Valid @RequestBody ExpenseRequestDto expenseRequestDto) {

        expenseService.updateExpense(userId, expenseId, expenseRequestDto);
        return ApiResponse.noContent();
    }

    @DeleteMapping("/{expenseId}")
    public ApiResponse<String> deleteExpense(
            @RequestAttribute Long userId,
            @PathVariable Long expenseId,
            @RequestBody Long categoryId) {

        expenseService.deleteExpense(userId, categoryId, expenseId);
        return ApiResponse.noContent();
    }
}
