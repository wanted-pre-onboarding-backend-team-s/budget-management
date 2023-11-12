package com.saving.expense.controller;

import com.saving.common.response.ApiResponse;
import com.saving.expense.dto.ExpenseRequestDto;
import com.saving.expense.dto.ExpenseResponseDto;
import com.saving.expense.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/api/v1")
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping("/categories/{categoryId}/expenses")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ExpenseResponseDto> createExpense(
            @RequestAttribute Long userId,
            @PathVariable Long categoryId,
            @Valid @RequestBody ExpenseRequestDto expenseRequestDto) {

        return ApiResponse.created(
                expenseService.createExpense(userId, categoryId, expenseRequestDto));
    }

    @PutMapping("/categories/{categoryId}/expenses/{expenseId}")
    public ApiResponse<String> updateExpense(
            @RequestAttribute Long userId,
            @PathVariable Long categoryId,
            @PathVariable Long expenseId,
            @Valid @RequestBody ExpenseRequestDto expenseRequestDto) {

        expenseService.updateExpense(userId, categoryId, expenseId, expenseRequestDto);
        return ApiResponse.noContent();
    }

    @DeleteMapping("/categories/{categoryId}/expenses/{expenseId}")
    public ApiResponse<String> deleteExpense(
            @RequestAttribute Long userId,
            @PathVariable Long categoryId,
            @PathVariable Long expenseId) {

        expenseService.deleteExpense(userId, categoryId, expenseId);
        return ApiResponse.noContent();
    }

    @GetMapping("/categories/{categoryId}/expenses/{expenseId}")
    public ApiResponse<ExpenseResponseDto> getExpense(
            @RequestAttribute Long userId,
            @PathVariable Long categoryId,
            @PathVariable Long expenseId) {

        return ApiResponse.ok(
                expenseService.getExpense(userId, categoryId, expenseId));
    }
}
