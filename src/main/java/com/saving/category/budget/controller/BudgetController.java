package com.saving.category.budget.controller;

import com.saving.category.budget.dto.BudgetRequestDto;
import com.saving.category.budget.dto.CreatedBudgetResponseDto;
import com.saving.category.budget.service.BudgetService;
import com.saving.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "예산", description = "예산 관련 API")
@RequestMapping("/api/v1/categories/{categoryId}/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    @Operation(summary = "예산 생성", description = "해당 카테고리의 예산을 생성합니다.")
    @PostMapping
    public ApiResponse<CreatedBudgetResponseDto> createBudget(
            @RequestAttribute Long userId,

            @Schema(description = "카테고리 아이디")
            @PathVariable Long categoryId,

            @Valid @RequestBody BudgetRequestDto budgetRequestDto) {

        return ApiResponse.created(
                budgetService.createBudget(userId, categoryId, budgetRequestDto));
    }

    @Operation(summary = "예산 수정", description = "해당 카테고리의 예산을 수정합니다.")
    @PutMapping("/{budgetId}")
    public ApiResponse<String> updateBudget(
            @RequestAttribute Long userId,

            @Schema(description = "카테고리 아이디")
            @PathVariable Long categoryId,

            @Schema(description = "예산 아이디")
            @PathVariable Long budgetId,

            @Valid @RequestBody BudgetRequestDto budgetRequestDto) {

        budgetService.updateBudget(userId, categoryId, budgetId, budgetRequestDto);
        return ApiResponse.noContent();
    }
}
