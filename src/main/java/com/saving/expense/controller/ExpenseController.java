package com.saving.expense.controller;

import com.saving.common.response.ApiResponse;
import com.saving.expense.dto.CreateExpenseRequestDto;
import com.saving.expense.dto.CreatedExpenseResponseDto;
import com.saving.expense.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
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
            @RequestBody CreateExpenseRequestDto createExpenseRequestDto) {

        return ApiResponse.created(expenseService.createExpense(createExpenseRequestDto));
    }
}
