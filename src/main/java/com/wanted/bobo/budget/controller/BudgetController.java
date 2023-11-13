package com.wanted.bobo.budget.controller;

import com.wanted.bobo.budget.dto.BudgetRecommendationResponse;
import com.wanted.bobo.budget.dto.BudgetRequest;
import com.wanted.bobo.budget.dto.BudgetResponse;
import com.wanted.bobo.budget.service.BudgetService;
import com.wanted.bobo.budget.service.BudgetStatService;
import com.wanted.bobo.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "예산 관리")
@RestController
@RequestMapping("/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;
    private final BudgetStatService budgetStatService;

    @GetMapping("/rec/{total_amount}")
    public ApiResponse<BudgetRecommendationResponse> recBudget(
            @PathVariable("total_amount") int amount) {
        return ApiResponse.ok(budgetStatService.recommendBudget(amount));
    }

    @GetMapping
    public ApiResponse<List<BudgetResponse>> getBudgets(
            @RequestAttribute Long userId) {
        return ApiResponse.ok(budgetService.getBudgets(userId));
    }

    @PostMapping
    public ApiResponse<BudgetResponse> makeBudget(
            @RequestAttribute Long userId,
            @Valid @RequestBody BudgetRequest request) {
        return ApiResponse.created(budgetService.makeBudget(userId, request));
    }

    @PutMapping("/{id}")
    public ApiResponse<BudgetResponse> reviseBudget(
            @RequestAttribute Long userId,
            @PathVariable("id") Long budgetId,
            @Valid @RequestBody BudgetRequest request) {
        return ApiResponse.created(budgetService.reviseBudget(userId, budgetId, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> reviseBudget(
            @RequestAttribute Long userId,
            @PathVariable("id") Long budgetId) {
        budgetService.removeBudget(userId, budgetId);
        return ApiResponse.noContent();
    }
}
