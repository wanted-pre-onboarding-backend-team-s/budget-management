package jaringobi.domain.expense;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jaringobi.domain.budget.Money;
import jaringobi.domain.category.Category;
import jaringobi.domain.user.User;
import jaringobi.exception.expense.ExpenseNullArgumentException;
import jaringobi.exception.expense.ExpenseNullUserException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExpenseTest {

    @Test
    @DisplayName("Builder 를 이용해서 Expense 객체 생성 성공")
    void builder() {
        assertThatCode(() -> Expense.builder()
                .memo("memo")
                .money(new Money(100))
                .user(User.builder()
                        .username("username")
                        .password("password123!")
                        .build()
                )
                .expenseAt(LocalDateTime.now())
                .category(Category.builder()
                        .id(1L)
                        .name("간식")
                        .build())
                .build()
        ).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("User 없이 Builder 로 Expense 객체 시 예외 던짐")
    void throwExceptionNullUser() {
        assertThatThrownBy(() -> Expense.builder()
                .memo("memo")
                .money(new Money(100))
                .user(null)
                .expenseAt(LocalDateTime.now())
                .category(Category.builder()
                        .id(1L)
                        .name("간식")
                        .build())
                .build()
        ).isInstanceOf(ExpenseNullUserException.class);
    }

    @Test
    @DisplayName("Money, Category, ExpenseAt 없이 Builder 로 Expense 객체 시 예외 던짐")
    void throwExceptionNullArgument() {

        // expenseAt is null
        assertThatThrownBy(() -> Expense.builder()
                .memo("memo")
                .money(new Money(100))
                .user(User.builder()
                        .username("username")
                        .password("password123!")
                        .build())
                .expenseAt(null)
                .category(Category.builder()
                        .id(1L)
                        .name("간식")
                        .build())
                .build()
        ).isInstanceOf(ExpenseNullArgumentException.class);

        // category is null
        assertThatThrownBy(() -> Expense.builder()
                .memo("memo")
                .money(new Money(100))
                .user(User.builder()
                        .username("username")
                        .password("password123!")
                        .build())
                .expenseAt(LocalDateTime.now())
                .category(null)
                .build()
        ).isInstanceOf(ExpenseNullArgumentException.class);

        // money is null
        assertThatThrownBy(() -> Expense.builder()
                .memo("memo")
                .money(null)
                .user(User.builder()
                        .username("username")
                        .password("password123!")
                        .build())
                .expenseAt(LocalDateTime.now())
                .category(Category.builder()
                        .id(1L)
                        .name("간식")
                        .build())
                .build()
        ).isInstanceOf(ExpenseNullArgumentException.class);
    }
}