package com.saving.expense.controller;

import com.saving.common.response.ApiResponse;
import com.saving.expense.dto.ExpenseListResponseDto;
import com.saving.expense.dto.ExpenseRequestDto;
import com.saving.expense.dto.ExpenseResponseDto;
import com.saving.expense.dto.ExpenseStatsResponseDto;
import com.saving.expense.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "지출", description = "지출 관련 API")
@RequestMapping("/api/v1/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Operation(summary = "지출 기록", description = "지출 기록을 생성합니다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ExpenseResponseDto> createExpense(
            @RequestAttribute Long userId,
            @Valid @RequestBody ExpenseRequestDto expenseRequestDto) {

        return ApiResponse.created(
                expenseService.createExpense(userId, expenseRequestDto));
    }

    @Operation(summary = "지출 기록 수정", description = "해당 지출 기록을 수정합니다.")
    @PutMapping("/{expenseId}")
    public ApiResponse<String> updateExpense(
            @RequestAttribute Long userId,

            @Schema(description = "지출 아이디")
            @PathVariable Long expenseId,

            @Valid @RequestBody ExpenseRequestDto expenseRequestDto) {

        expenseService.updateExpense(userId, expenseId, expenseRequestDto);
        return ApiResponse.noContent();
    }

    @Operation(summary = "지출 기록 상세 조회", description = "해당 지출 기록에 대한 상세 정보를 조회합니다.")
    @GetMapping("/{expenseId}")
    public ApiResponse<ExpenseResponseDto> getExpense(
            @RequestAttribute Long userId,

            @Schema(description = "지출 아이디")
            @PathVariable Long expenseId) {

        return ApiResponse.ok(
                expenseService.getExpense(userId, expenseId));
    }

    @Operation(summary = "지출 목록 조회", description = "지출 목록을 조회하고, 해당 지출 목록의 합계와 카테고리별 합계를 조회할 수 있습니다.")
    @GetMapping
    public ApiResponse<ExpenseListResponseDto> expenseList(
            @RequestAttribute Long userId,

            @Schema(description = "조회 시작날짜")
            @RequestParam(name = "start-date") String startDate,

            @Schema(description = "조회 마지막날짜")
            @RequestParam(name = "end-date") String endDate,

            @Schema(description = "카테고리 아이디")
            @RequestParam(name = "category-id", required = false) Long categoryId,

            @Schema(description = "최소 지출액")
            @RequestParam(name = "min-amount", required = false) Integer minAmount,

            @Schema(description = "최대 지출액")
            @RequestParam(name = "max-amount", required = false) Integer maxAmount) {

        return ApiResponse.ok(
                expenseService.expenseList(userId, startDate, endDate,
                        categoryId, minAmount, maxAmount));
    }

    @Operation(summary = "지출 기록 삭제", description = "해당 지출 기록을 삭제합니다.")
    @DeleteMapping("/{expenseId}")
    public ApiResponse<String> deleteExpense(
            @RequestAttribute Long userId,

            @Schema(description = "지출 아이디")
            @PathVariable Long expenseId) {

        expenseService.deleteExpense(userId, expenseId);
        return ApiResponse.noContent();
    }

    @Operation(summary = "지출 통계", description = "지난달, 지난요일, 다른 사용자 대비 소비율을 조회할 수 있습니다.")
    @GetMapping("/stats")
    public ApiResponse<ExpenseStatsResponseDto> expenseStats(@RequestAttribute Long userId) {
        return ApiResponse.ok(expenseService.expenseStats(userId));
    }
}
