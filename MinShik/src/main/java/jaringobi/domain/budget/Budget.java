package jaringobi.domain.budget;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jaringobi.domain.BaseTimeEntity;
import jaringobi.domain.user.User;
import jaringobi.exception.budget.InvalidBudgetException;
import java.util.ArrayList;
import java.util.List;
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

    @Column(name = "budget_month", nullable = false)
    private BudgetYearMonth yearMonth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_budget_to_user"))
    private User user;

    @OneToMany(mappedBy = "budget", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CategoryBudget> categoryBudgets = new ArrayList<>();

    @Builder
    public Budget(final Long id, final BudgetYearMonth yearMonth, final User user,
            final List<CategoryBudget> categoryBudgets) {
        verifyYearNonEmpty(yearMonth);
        this.id = id;
        this.yearMonth = yearMonth;
        if (Objects.nonNull(user)) {
            setUser(user);
        }

        if (Objects.nonNull(categoryBudgets)) {
            setCategoryBudgets(categoryBudgets);
        }
    }

    private void verifyYearNonEmpty(BudgetYearMonth yearMonth) {
        if (Objects.isNull(yearMonth) || yearMonth.isEmptyMonth()) {
            throw new InvalidBudgetException();
        }
    }

    public void setCategoryBudgets(List<CategoryBudget> categoryBudgets) {
        if (!this.categoryBudgets.isEmpty()) {
            throw new InvalidBudgetException();
        }
        this.categoryBudgets = categoryBudgets.stream()
                .map(categoryBudget -> CategoryBudget.withBudget(this, categoryBudget))
                .toList();
    }

    public void setUser(User user) {
        if (Objects.nonNull(this.user)) {
            throw new InvalidBudgetException();
        }

        if (Objects.nonNull(user)) {
            this.user = user;
        }
    }

    public Long getId() {
        return id;
    }

    public BudgetYearMonth getYearMonth() {
        return yearMonth;
    }

    public List<CategoryBudget> getCategoryBudgets() {
        return categoryBudgets;
    }
}
