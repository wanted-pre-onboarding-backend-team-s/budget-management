package com.wanted.bobo.expense.service;

import com.wanted.bobo.expense.domain.ExpenseRepository;
import com.wanted.bobo.expense.dto.ExpenseRequest;
import com.wanted.bobo.expense.dto.ExpenseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    @Transactional
    public ExpenseResponse registerExpense(Long userId, ExpenseRequest request) {
       return ExpenseResponse.from(expenseRepository.save(request.toEntity(userId)));
    }
}
