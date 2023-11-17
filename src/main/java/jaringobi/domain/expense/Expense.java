package jaringobi.domain.expense;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jaringobi.domain.BaseTimeEntity;
import jaringobi.domain.budget.Money;
import jaringobi.domain.category.Category;
import jaringobi.domain.user.User;
import jaringobi.exception.expense.ExpenseNullArgumentException;
import jaringobi.exception.expense.ExpenseNullUserException;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EXPENSE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Expense extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "MEMO")
    private String memo;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "AMOUNT"))
    private Money money;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false,
            foreignKey = @ForeignKey(name = "FK_expense_user_id"))
    private User owner;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", nullable = false,
            foreignKey = @ForeignKey(name = "FK_expense_category_id"))
    private Category category;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "EXPENSE_AT", nullable = false)
    private LocalDateTime expenseAt;

    @Builder
    public Expense(final Long id, final String memo, final Money money, final User user, final Category category, final LocalDateTime expenseAt) {
        verifyNonNullArgument(money, category, expenseAt);
        verifyNonNullUser(user);
        this.id = id;
        this.memo = memo;
        this.owner = user;
        this.money = money;
        this.category = category;
        this.expenseAt = expenseAt;
    }

    private void verifyNonNullUser(User user) {
        if (Objects.isNull(user)) {
            throw new ExpenseNullUserException();
        }
    }

    private void verifyNonNullArgument(Money money, Category category, LocalDateTime expenseAt) {
        if (Objects.isNull(money) || Objects.isNull(category) || Objects.isNull(expenseAt)) {
            throw new ExpenseNullArgumentException();
        }
    }

}
