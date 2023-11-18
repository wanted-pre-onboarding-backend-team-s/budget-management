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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jaringobi.domain.BaseTimeEntity;
import jaringobi.domain.budget.Money;
import jaringobi.domain.category.Category;
import jaringobi.domain.user.AppUser;
import jaringobi.domain.user.User;
import jaringobi.exception.expense.ExpenseNullArgumentException;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "expense")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Expense extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Lob
    @Column(name = "memo", columnDefinition = "MEDIUMTEXT")
    private String memo;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "AMOUNT", nullable = false))
    private Money money;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_expense_user_id"))
    private User owner;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_expense_category_id"))
    private Category category;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "expense_at", nullable = false)
    private LocalDateTime expenseAt;

    @Column(name = "is_exclude_in_total", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isExcludeInTotal = false;

    @Builder
    public Expense(final Long id, final String memo, final Money money, final User user, final Category category,
            final LocalDateTime expenseAt, final Boolean exclude) {
        verifyNonNullArgument(money, category, expenseAt);
        this.id = id;
        this.memo = memo;
        if (Objects.nonNull(money)) {
            setMoney(money);
        }

        this.expenseAt = expenseAt;
        if (Objects.nonNull(exclude)) {
            setExclude(exclude);
        }

        if (Objects.nonNull(user)) {
            setUser(user);
        }

        if (Objects.nonNull(category)) {
            setCategory(category);
        }
    }

    private void setMoney(Money money) {
        this.money = new Money(money.getAmount());
    }

    private void setUser(User user) {
        if (Objects.isNull(this.owner)) {
            this.owner = user;
        }
    }

    private void setExclude(boolean exclude) {
        this.isExcludeInTotal = exclude;
    }

    private void verifyNonNullArgument(Money money, Category category, LocalDateTime expenseAt) {
        if (Objects.isNull(money) || Objects.isNull(category) || Objects.isNull(expenseAt)) {
            throw new ExpenseNullArgumentException();
        }
    }

    public Long getId() {
        return id;
    }

    public boolean isOwnerOf(AppUser appUser) {
        return owner.isSame(appUser);
    }

    public void modify(Expense expense) {
        this.memo = expense.memo;
        this.money = new Money(expense.money.getAmount());
        this.expenseAt = expense.expenseAt;
        this.isExcludeInTotal = expense.isExcludeInTotal;
        if (Objects.nonNull(expense.getCategory())) {
            setCategory(expense.getCategory());
        }
    }

    private void setCategory(Category category) {
        if (Objects.isNull(this.category)) {
            this.category = category;
            return;
        }

        if (!this.category.isSameAs(category)) {
            this.category = category;
        }
    }

    public String getMemo() {
        return memo;
    }

    public Money getMoney() {
        return money;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDateTime getExpenseAt() {
        return expenseAt;
    }

    public boolean isExcludeInTotal() {
        return isExcludeInTotal;
    }
}
