package com.saving.budget.controller;

import com.saving.budget.dto.CreateBudgetRequestDto;
import com.saving.budget.dto.CreatedBudgetResponseDto;
import com.saving.budget.service.BudgetService;
import com.saving.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    public ApiResponse<CreatedBudgetResponseDto> createBudget(
            @RequestAttribute Long userId,
            @Valid @RequestBody CreateBudgetRequestDto createBudgetRequestDto) {

        return ApiResponse.created(budgetService.createBudget(userId, createBudgetRequestDto));
    }
}
