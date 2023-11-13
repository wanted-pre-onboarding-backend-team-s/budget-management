package jaringobi.domain.budget;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import jaringobi.exception.budget.InvalidBudgetException;
import jaringobi.exception.budget.LowBudgetException;
import java.lang.reflect.Field;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BudgetTest {

    @Test
    @DisplayName("Builder 로 CategoryBudget 객체 생성 성공")
    void successCreateBudget() {
        assertThatCode(() -> CategoryBudget.builder()
                .amount(new Money(10000))
                .categoryId(1L)
                .build())
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Money 가 Null 일 경우 CategoryBudget 객체 생성 시 예외를 던진다.")
    void throwExceptionCreateBudgetWhenMoneyIsNull() {
        assertThatThrownBy(() -> CategoryBudget.builder()
                .amount(null)
                .categoryId(1L)
                .build())
                .isInstanceOf(InvalidBudgetException.class);
    }

    @Test
    @DisplayName("categoryId 가 Null 일 경우 CategoryBudget 객체 생성 시 예외를 던진다.")
    void throwExceptionCreateBudgetWhenCategoryIdIsNull() {
        assertThatThrownBy(() -> CategoryBudget.builder()
                .amount(new Money(100))
                .categoryId(null)
                .build())
                .isInstanceOf(InvalidBudgetException.class);
    }

    @Test
    @DisplayName("Money 가 0원 일 경우 CategoryBudget 객체 생성 시 예외를 던진다.")
    void throwExceptionCreateBudgetWhenMoneyIsZero() {
        assertThatThrownBy(() -> CategoryBudget.builder()
                .amount(new Money(0))
                .categoryId(1L)
                .build())
                .isInstanceOf(LowBudgetException.class);
    }
}
