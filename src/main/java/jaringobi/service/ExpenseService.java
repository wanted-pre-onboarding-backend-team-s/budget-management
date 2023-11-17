package jaringobi.service;

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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void addExpense(AddExpenseRequest addExpenseRequest,final AppUser appUser) {
        final User user = findUser(appUser);
        final Category category = findCategory(addExpenseRequest.getCategoryId());
        Expense expense = convertToExpense(addExpenseRequest, user, category);
        expenseRepository.save(expense);
    }

    private Expense convertToExpense(AddExpenseRequest addExpenseRequest, User user, Category category) {
        final LocalDateTime expandAt = addExpenseRequest.getExpenseDateTime();
        final Money money = new Money(addExpenseRequest.getExpenseMount());
        final String memo = addExpenseRequest.getMemo();
        return Expense.builder()
                .expenseAt(expandAt)
                .money(money)
                .memo(memo)
                .category(category)
                .user(user)
                .build();
    }

    private Category findCategory(long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);
    }

    private User findUser(AppUser appUser) {
        return userRepository.findById(appUser.userId())
                .orElseThrow(UserNotFoundException::new);
    }
}
