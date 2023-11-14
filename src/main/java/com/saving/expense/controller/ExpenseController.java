package com.saving.expense.controller;

import com.saving.common.response.ApiResponse;
import com.saving.expense.dto.ExpenseListResponseDto;
import com.saving.expense.dto.ExpenseRequestDto;
import com.saving.expense.dto.ExpenseResponseDto;
import com.saving.expense.dto.ExpenseStatsResponseDto;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ExpenseResponseDto> createExpense(
            @RequestAttribute Long userId,
            @Valid @RequestBody ExpenseRequestDto expenseRequestDto) {

        return ApiResponse.created(
                expenseService.createExpense(userId, expenseRequestDto));
    }

    @PutMapping("/{expenseId}")
    public ApiResponse<String> updateExpense(
            @RequestAttribute Long userId,
            @PathVariable Long expenseId,
            @Valid @RequestBody ExpenseRequestDto expenseRequestDto) {

        expenseService.updateExpense(userId, expenseId, expenseRequestDto);
        return ApiResponse.noContent();
    }

    @GetMapping("/{expenseId}")
    public ApiResponse<ExpenseResponseDto> getExpense(
            @RequestAttribute Long userId,
            @PathVariable Long expenseId) {

        return ApiResponse.ok(
                expenseService.getExpense(userId, expenseId));
    }

    @GetMapping
    public ApiResponse<ExpenseListResponseDto> expenseList(
            @RequestAttribute Long userId,
            @RequestParam(name = "start-date") String startDate,
            @RequestParam(name = "end-date") String endDate,
            @RequestParam(name = "category-id", required = false) Long categoryId,
            @RequestParam(name = "min-amount", required = false) Boolean minAmount,
            @RequestParam(name = "max-amount", required = false) Boolean maxAmount) {

        return ApiResponse.ok(
                expenseService.expenseList(userId, startDate, endDate,
                        categoryId, minAmount, maxAmount));
    }

    @DeleteMapping("/{expenseId}")
    public ApiResponse<String> deleteExpense(
            @RequestAttribute Long userId,
            @PathVariable Long expenseId) {

        expenseService.deleteExpense(userId, expenseId);
        return ApiResponse.noContent();
    }

    @GetMapping("/stats")
    public ApiResponse<ExpenseStatsResponseDto> expenseStats(@RequestAttribute Long userId) {
        return ApiResponse.ok(expenseService.expenseStats(userId));
    }
}
