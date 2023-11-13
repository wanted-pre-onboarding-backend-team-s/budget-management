package com.wanted.bobo.expense.contoller;

import com.wanted.bobo.common.response.ApiResponse;
import com.wanted.bobo.expense.dto.ExpenseRequest;
import com.wanted.bobo.expense.dto.ExpenseResponse;
import com.wanted.bobo.expense.service.ExpenseService;
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

@Tag(name = "지출 관리")
@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ApiResponse<ExpenseResponse> registerExpense(
            @RequestAttribute Long userId,
            @Valid @RequestBody ExpenseRequest request) {
        return ApiResponse.created(expenseService.registerExpense(userId, request));
    }

    @PutMapping("/{id}")
    public ApiResponse<ExpenseResponse> modifyBudget(
            @RequestAttribute Long userId,
            @PathVariable("id") Long expenseId,
            @Valid @RequestBody ExpenseRequest request) {
        return ApiResponse.created(expenseService.modifyExpense(userId, expenseId, request));
    }
}
