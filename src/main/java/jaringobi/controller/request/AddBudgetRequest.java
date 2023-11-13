package jaringobi.controller.request;

import jakarta.validation.Valid;
import jaringobi.domain.budget.Budget;
import jaringobi.domain.budget.BudgetYearMonth;
import jaringobi.domain.budget.CategoryBudget;
import jaringobi.domain.budget.Money;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AddBudgetRequest {

    private List<BudgetByCategoryRequest> budgetByCategories;
    private String month;

    @Builder
    public AddBudgetRequest(List<BudgetByCategoryRequest> budgetByCategories, String month) {
        this.budgetByCategories = budgetByCategories;
        this.month = month;
    }

    public Budget toBudget() {
        return Budget.builder()
                .yearMonth(BudgetYearMonth.fromString(month))
                .build();
    }

    public List<CategoryBudget> categoryBudgets() {
        return budgetByCategories.stream()
                .map(it -> CategoryBudget
                        .builder()
                        .categoryId(it.getCategoryId())
                        .amount(new Money(it.getMoney()))
                        .build())
                .toList();
    }
}
