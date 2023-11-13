package jaringobi.domain.budget;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jaringobi.exception.budget.LowBudgetException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Money {

    @Column(name = "amount", nullable = false)
    private int amount;

    public Money(int amount) {
        verifyMoneyValue(amount);
        this.amount = amount;
    }

    private void verifyMoneyValue(int amount) {
        if (amount == 0) {
            throw new LowBudgetException();
        }
    }
}
