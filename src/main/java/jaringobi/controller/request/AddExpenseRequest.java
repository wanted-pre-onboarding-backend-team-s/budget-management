package jaringobi.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jaringobi.domain.budget.Money;
import jaringobi.domain.expense.Expense;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddExpenseRequest {

    private String memo;

    @PositiveOrZero
    private int expenseMount;

    @NotNull
    private LocalDateTime expenseDateTime;
    private Boolean excludeTotalExpense;

    @PositiveOrZero
    private int categoryId;

    public Expense toExpense() {
        return Expense.builder()
                .expenseAt(expenseDateTime)
                .money(new Money(expenseMount))
                .memo(memo)
                .build();
    }

    @Builder
    public AddExpenseRequest(String memo, int expenseMount, LocalDateTime expenseDateTime, Boolean excludeTotalExpense,
            int categoryId) {
        this.memo = memo;
        this.expenseMount = expenseMount;
        this.expenseDateTime = expenseDateTime;
        this.excludeTotalExpense = excludeTotalExpense;
        this.categoryId = categoryId;
    }
}
