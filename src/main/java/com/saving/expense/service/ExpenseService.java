package com.saving.expense.service;

import com.saving.category.budget.domain.repository.BudgetRepository;
import com.saving.category.budget.dto.ResultFoundCategoryAndBudgetDto;
import com.saving.category.domain.repository.CategoryRepository;
import com.saving.category.exception.CategoryNotFoundException;
import com.saving.category.exception.MismatchedCategoryIdAndUserIdException;
import com.saving.expense.domain.entity.Expense;
import com.saving.expense.domain.repository.ExpenseRepository;
import com.saving.expense.dto.ExpenseListResponseDto;
import com.saving.expense.dto.ExpenseRequestDto;
import com.saving.expense.dto.ExpenseResponseDto;
import com.saving.expense.dto.SimpleExpenseDto;
import com.saving.expense.dto.TodayExpenseNoticeDto;
import com.saving.expense.dto.TotalExpenseByCategory;
import com.saving.expense.exception.NotExistExpenseInCategoryException;
import com.saving.expense.vo.CalcTodayCategoryExpenseVo;
import com.saving.user.domain.entity.User;
import com.saving.user.domain.repository.UserRepository;
import com.saving.webhook.WebhookClient;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final BudgetRepository budgetRepository;
    private final ExpenseRepository expenseRepository;
    private final WebhookClient webhookClient;

    @Value("${webhook.api.url}")
    private String webhookUrl;

    @Transactional
    public ExpenseResponseDto createExpense(
            Long userId, ExpenseRequestDto expenseRequestDto) {

        existUserAndCategory(expenseRequestDto.getCategoryId(), userId);

        if (!categoryRepository.existsById(expenseRequestDto.getCategoryId())) {
            throw new CategoryNotFoundException();
        }

        return new ExpenseResponseDto(
                expenseRepository.save(expenseRequestDto.toEntity()));
    }

    @Transactional
    public void updateExpense(
            Long userId, Long expenseId, ExpenseRequestDto expenseRequestDto) {

        existUserAndCategory(expenseRequestDto.getCategoryId(), userId);

        Expense savedExpense = expenseRepository
                .findByIdAndCategoryId(expenseId, expenseRequestDto.getCategoryId())
                .orElseThrow(NotExistExpenseInCategoryException::new);

        savedExpense.changeExpense(expenseRequestDto);
    }

    @Transactional
    public void deleteExpense(Long userId, Long expenseId) {

        if (!expenseRepository.existsByIdAndUserId(expenseId, userId)) {
            throw new NotExistExpenseInCategoryException();
        }
        expenseRepository.deleteById(expenseId);
    }

    @Transactional(readOnly = true)
    public ExpenseResponseDto getExpense(Long userId, Long expenseId) {

        return expenseRepository.findByIdAndUserId(expenseId, userId)
                .orElseThrow(NotExistExpenseInCategoryException::new);
    }

    @Transactional(readOnly = true)
    public ExpenseListResponseDto expenseList(
            Long userId, String startDate, String endDate,
            Long categoryId, Boolean minAmount, Boolean maxAmount) {

        List<SimpleExpenseDto> simpleExpenseList = expenseRepository.listOfTimeBasedExpense(userId,
                startDate, endDate,
                categoryId, minAmount, maxAmount);

        Long totalExpense = expenseRepository.totalExpense(userId, startDate, endDate, categoryId,
                minAmount,
                maxAmount);

        List<TotalExpenseByCategory> totalExpenseByCategoryList = expenseRepository.listOfCategoryBasedExpense(
                userId, startDate, endDate, categoryId,
                minAmount, maxAmount);

        return ExpenseListResponseDto.builder()
                .expenseList(simpleExpenseList)
                .totalExpense(totalExpense)
                .totalExpenseByCategoryList(totalExpenseByCategoryList)
                .build();
    }

    private void existUserAndCategory(Long categoryId, Long userId) {
        if (!categoryRepository.existsByIdAndUserId(categoryId, userId)) {
            throw new MismatchedCategoryIdAndUserIdException();
        }
    }

    @Scheduled(cron = "0 0 20 * * *", zone = "Asia/Seoul")
    public void sendDiscordWebhookTodayExpenseNoticeMessage() {
        log.debug("스케줄링 작업 시작");
        List<User> userList = userRepository.findByIsTodayExpenseNoticeIsTrue();

        userList.stream()
                .parallel()
                .forEach(this::sendAsyncWebhookMessage);
    }

    @Async
    public void sendAsyncWebhookMessage(User user) {

        List<ResultFoundCategoryAndBudgetDto> foundByCategoryIdAndCategoryNameAndAmount =
                budgetRepository.findByCategoryAndBudget(
                        user.getId(), String.valueOf(YearMonth.now().atDay(1)));

        LocalDate today = LocalDate.now();

        List<CalcTodayCategoryExpenseVo> list = new ArrayList<>();
        for (ResultFoundCategoryAndBudgetDto resultFoundCategoryAndBudgetDto
                : foundByCategoryIdAndCategoryNameAndAmount) {

            Long categoryId = resultFoundCategoryAndBudgetDto.getCategoryId();
            String categoryName = resultFoundCategoryAndBudgetDto.getCategoryName();
            int categoryBudget = resultFoundCategoryAndBudgetDto.getAmount();

            LocalDate firstDayOfMonth = today.withDayOfMonth(1);
            Long categoryTodayExpenseSum = expenseRepository.findCategoryTodayExpenseSum(categoryId,
                    firstDayOfMonth.toString(), today.toString()).orElse(0L);

            int categoryTodayExpense = expenseRepository.findCategoryTodayExpense(categoryId,
                    today.toString()).orElse(0);

            Long categoryTotalExpenseUpToYesterday = categoryTodayExpenseSum - categoryTodayExpense;

            list.add(new CalcTodayCategoryExpenseVo(
                    categoryName, categoryBudget,
                    categoryTotalExpenseUpToYesterday, categoryTodayExpense));
        }

        Long todayTotalExpense = expenseRepository
                .todayTotalExpense(user.getId(), today.toString()).orElse(0L);

        webhookClient.sendTodayExpenseNoticeMessage(
                this.webhookUrl, user.getUsername(), new TodayExpenseNoticeDto(todayTotalExpense, list));
    }
}
