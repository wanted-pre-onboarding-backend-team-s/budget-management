package com.saving.category.budget.controller;

import com.saving.category.budget.dto.BudgetRequestDto;
import com.saving.category.budget.dto.CreatedBudgetResponseDto;
import com.saving.category.budget.service.BudgetService;
import com.saving.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories/{categoryId}/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    public ApiResponse<CreatedBudgetResponseDto> createBudget(
            @RequestAttribute Long userId,
            @PathVariable Long categoryId,
            @Valid @RequestBody BudgetRequestDto budgetRequestDto) {

        return ApiResponse.created(
                budgetService.createBudget(userId, categoryId, budgetRequestDto));
    }

    @PutMapping("/{budgetId}")
    public ApiResponse<String> updateBudget(
            @RequestAttribute Long userId,
            @PathVariable Long categoryId,
            @PathVariable Long budgetId,
            @Valid @RequestBody BudgetRequestDto budgetRequestDto) {

        budgetService.updateBudget(userId, categoryId, budgetId, budgetRequestDto);
        return ApiResponse.noContent();
    }
}
