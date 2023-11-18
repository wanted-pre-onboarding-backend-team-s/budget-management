package jaringobi.domain.expense;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jaringobi.domain.budget.Money;
import jaringobi.domain.category.Category;
import jaringobi.domain.user.AppUser;
import jaringobi.domain.user.User;
import jaringobi.exception.expense.ExpenseNullArgumentException;
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

    @Test
    @DisplayName("User 와 AppUser 가 같으면 isOwnerOf 는 True 반환")
    void returnTrueIsOwnerOfUserIdEqualAppUser() {
        User user = User.builder()
                .id(1L)
                .username("username")
                .password("password123!")
                .build();
        Expense expense = Expense.builder()
                .memo("memo")
                .money(new Money(100))
                .user(user)
                .expenseAt(LocalDateTime.now())
                .category(Category.builder()
                        .id(1L)
                        .name("간식")
                        .build())
                .build();

        AppUser appUser = new AppUser(1L);

        // When
        boolean isOwner = expense.isOwnerOf(appUser);

        // Then
        assertThat(isOwner).isTrue();
    }

    @Test
    @DisplayName("User 와 AppUser 다르면 isOwnerOf 는 False 반환")
    void returnFalseIsOwnerOfUserIdNotEqualAppUser() {
        User user = User.builder()
                .id(1L)
                .username("username")
                .password("password123!")
                .build();
        Expense expense = Expense.builder()
                .memo("memo")
                .money(new Money(100))
                .user(user)
                .expenseAt(LocalDateTime.now())
                .category(Category.builder()
                        .id(1L)
                        .name("간식")
                        .build())
                .build();

        AppUser appUser = new AppUser(2L);

        // When
        boolean isOwner = expense.isOwnerOf(appUser);

        // Then
        assertThat(isOwner).isFalse();
    }

    @Test
    @DisplayName("Expense 수정 성공")
    void modifyExpense() {
        Expense expense = Expense.builder()
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
                .build();

        Expense modifyExpense = Expense.builder()
                .memo("memo2")
                .money(new Money(100_000))
                .expenseAt(LocalDateTime.of(2029, 12, 25, 0, 0))
                .category(Category.builder()
                        .id(100L)
                        .name("기타")
                        .build())
                .build();

        // When
        expense.modify(modifyExpense);

        // Then
        assertThat(expense.getExpenseAt()).isEqualTo(modifyExpense.getExpenseAt());
        assertThat(expense.getMoney().isSameAs(modifyExpense.getMoney())).isTrue();
        assertThat(expense.getCategory().isSameAs(modifyExpense.getCategory())).isFalse();
        assertThat(expense.getMemo()).isEqualTo(modifyExpense.getMemo());
    }
}