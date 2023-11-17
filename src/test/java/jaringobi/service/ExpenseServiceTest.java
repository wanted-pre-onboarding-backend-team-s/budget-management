package jaringobi.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jaringobi.controller.request.AddExpenseRequest;
import jaringobi.domain.budget.Money;
import jaringobi.domain.category.Category;
import jaringobi.domain.category.CategoryRepository;
import jaringobi.domain.expense.Expense;
import jaringobi.domain.expense.ExpenseRepository;
import jaringobi.domain.user.AppUser;
import jaringobi.domain.user.User;
import jaringobi.domain.user.UserRepository;
import jaringobi.exception.category.CategoryNotFoundException;
import jaringobi.exception.user.UserNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
public class ExpenseServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    ExpenseRepository expenseRepository;

    @InjectMocks
    ExpenseService expenseService;

    private final Category category = Category.builder()
            .id(1L)
            .name("식품")
            .build();

    private final AppUser appUser = new AppUser(1L);

    private final User user = User.builder()
            .username("username")
            .password("password")
            .build();

    private final AddExpenseRequest addExpenseRequest = AddExpenseRequest.builder()
            .categoryId(1L)
            .expenseMount(10000)
            .memo("memo")
            .expenseDateTime(LocalDateTime.now())
            .build();

    private final Expense mockExpense = Expense.builder()
            .expenseAt(addExpenseRequest.getExpenseDateTime())
            .money(new Money(addExpenseRequest.getExpenseMount()))
            .memo(addExpenseRequest.getMemo())
            .user(user)
            .category(category)
            .id(1L)
            .build();

    @Test
    @DisplayName("지출 추가하기 성공")
    void addExpense() throws NoSuchFieldException, IllegalAccessException {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(expenseRepository.save(any())).thenReturn(mockExpense);

        // When
        expenseService.addExpense(addExpenseRequest, appUser);

        // Verity
        verify(expenseRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("존재하지 않는 유저의 경우 지출 추가하기 실패")
    void throwExceptionAddExpenseNullUser() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> expenseService.addExpense(addExpenseRequest, appUser))
                .isInstanceOf(UserNotFoundException.class);

        // Verify
        verify(categoryRepository, times(0)).findById(any());
        verify(expenseRepository, times(0)).save(any());
    }

    @Test
    @DisplayName("존재하지 않는 카테고리의 경우 지출 추가하기 실패")
    void throwExceptionNullCategory() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> expenseService.addExpense(addExpenseRequest, appUser))
                .isInstanceOf(CategoryNotFoundException.class);

        // Verify
        verify(userRepository, times(1)).findById(any());
        verify(expenseRepository, times(0)).save(any());
    }
}
