package jaringobi.domain.budget;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jaringobi.domain.BaseTimeEntity;
import jaringobi.exception.budget.InvalidBudgetException;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "budget_by_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryBudget extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Money amount;

    @Column(nullable = false)
    private Long categoryId;

    @Builder
    public CategoryBudget(final Money amount, final Long categoryId) {
        validateBudget(amount, categoryId);
        this.amount = amount;
        this.categoryId = categoryId;
    }

    private void validateBudget(Money amount, Long categoryId) {
        if (Objects.isNull(amount) || Objects.isNull(categoryId)) {
            throw new InvalidBudgetException();
        }
    }
}
