package com.saving.expense.controller;

import com.saving.common.response.ApiResponse;
import com.saving.expense.dto.ExpenseRequestDto;
import com.saving.expense.dto.CreatedExpenseResponseDto;
import com.saving.expense.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
            @Valid @RequestBody ExpenseRequestDto expenseRequestDto) {

        return ApiResponse.created(expenseService.createExpense(expenseRequestDto));
    }

    @PutMapping("/{expenseId}")
    public ApiResponse<String> updateExpense(
            @PathVariable Long expenseId, @Valid @RequestBody ExpenseRequestDto expenseRequestDto) {
        expenseService.updateExpense(expenseId, expenseRequestDto);
        return ApiResponse.noContent();
    }
}
