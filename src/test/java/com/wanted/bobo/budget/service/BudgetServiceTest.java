package com.wanted.bobo.budget.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import com.wanted.bobo.budget.domain.Budget;
import com.wanted.bobo.budget.domain.BudgetRepository;
import com.wanted.bobo.budget.dto.BudgetRequest;
import com.wanted.bobo.budget.dto.BudgetResponse;
import com.wanted.bobo.budget.exception.DuplicateBudgetCategoryException;
import com.wanted.bobo.budget.exception.NotFoundBudgetException;
import com.wanted.bobo.budget.exception.NotMatchUserException;
import com.wanted.bobo.category.Category;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class BudgetServiceTest {

    @InjectMocks
    private BudgetService budgetService;

    @Mock
    private BudgetRepository budgetRepository;

    private static final Long TEST_USER_ID = 1L;
    private static final Long TEST_ANOTHER_USER_ID = 2L;
    private static final int TEST_AMOUNT = 100000;
    private static final String TEST_CATEGORY_CODE = "C001";
    private static final String TEST_UPDATE_CATEGORY_CODE = "C002";

    private Budget budget;

    @BeforeEach
    void setUp() {
        budget = Budget.builder()
                       .userId(TEST_USER_ID)
                       .amount(TEST_AMOUNT)
                       .category(Category.FOOD)
                       .build();
    }

    @DisplayName("예산 정보 조회 성공")
    @Test
    void get_my_budgets() {
        when(budgetRepository.findAllByUserId(any())).thenReturn(anyList());
        List<BudgetResponse> response = budgetService.getBudgets(TEST_USER_ID);
        assertThat(response).isEmpty();

    }

    @DisplayName("예산 설정 성공")
    @Test
    void make_budget_success() {
        BudgetRequest request = new BudgetRequest(TEST_AMOUNT, TEST_CATEGORY_CODE);
        when(budgetRepository.existsByUserIdAndCategory(any(), any())).thenReturn(false);
        when(budgetRepository.save(any())).thenReturn(budget);

        BudgetResponse response = budgetService.makeBudget(TEST_USER_ID, request);

        assertThat(response.getAmount()).isEqualTo(request.getAmount());
        assertThat(response.getCategory()).isEqualTo(request.getCategory());
    }

    @DisplayName("예산 설정 실패 - 이미 해당 카테고리에 대한 예산이 저장되어 있을때")
    @Test
    void make_budget_fail_by_duplicate_category() {
        BudgetRequest request = new BudgetRequest(TEST_AMOUNT, TEST_CATEGORY_CODE);
        when(budgetRepository.existsByUserIdAndCategory(any(), any())).thenReturn(true);

        assertThatThrownBy(() -> budgetService.makeBudget(TEST_USER_ID, request))
                .isInstanceOf(DuplicateBudgetCategoryException.class);
    }

    @DisplayName("예산 수정 성공")
    @Test
    void revise_budget_success() {
        BudgetRequest request = new BudgetRequest(TEST_AMOUNT, TEST_UPDATE_CATEGORY_CODE);
        when(budgetRepository.findById(any())).thenReturn(Optional.of(budget));
        when(budgetRepository.existsByUserIdAndCategory(any(), any())).thenReturn(false);

        BudgetResponse response = budgetService.reviseBudget(TEST_USER_ID, budget.getId(), request);

        assertThat(response.getAmount()).isEqualTo(request.getAmount());
        assertThat(response.getCategory()).isEqualTo(request.getCategory());
    }

    @DisplayName("예산 수정 실패 - 예산 정보가 없을때")
    @Test
    void revise_budget_fail_by_no_budget() {
        BudgetRequest request = new BudgetRequest(TEST_AMOUNT, TEST_UPDATE_CATEGORY_CODE);
        when(budgetRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> budgetService.reviseBudget(TEST_USER_ID, budget.getId(), request))
                .isInstanceOf(NotFoundBudgetException.class);
    }

    @DisplayName("예산 수정 실패 - 작성자가 일치하지 않을때")
    @Test
    void revise_budget_fail_by_not_match_user() {
        BudgetRequest request = new BudgetRequest(TEST_AMOUNT, TEST_UPDATE_CATEGORY_CODE);
        when(budgetRepository.findById(any())).thenReturn(Optional.of(budget));

        assertThatThrownBy(() -> budgetService.reviseBudget(TEST_ANOTHER_USER_ID, budget.getId(), request))
                .isInstanceOf(NotMatchUserException.class);
    }

    @DisplayName("예산 삭제 성공")
    @Test
    void removeBudget() {
        when(budgetRepository.findById(any())).thenReturn(Optional.of(budget));
        budgetService.removeBudget(TEST_USER_ID, budget.getId());
    }
}