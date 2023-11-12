package jaringobi.domain.budget;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jaringobi.domain.BaseTimeEntity;
import jaringobi.exception.budget.InvalidBudgetException;
import java.util.Date;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "budget")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Budget extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Money amount;

    @Column(nullable = false)
    private Long categoryId;

    @Temporal(value = TemporalType.DATE)
    @Column(nullable = false)
    private Date budgetMonth;

    @Builder
    public Budget(final Money amount, final Long categoryId, final Date budgetMonth) {
        validateBudget(amount, categoryId, budgetMonth);
        this.amount = amount;
        this.categoryId = categoryId;
        this.budgetMonth = budgetMonth;
    }

    private void validateBudget(Money amount, Long categoryId, Date budgetMonth) {
        if (Objects.isNull(amount) || Objects.isNull(budgetMonth) || Objects.isNull(categoryId)) {
            throw new InvalidBudgetException();
        }
    }
}
