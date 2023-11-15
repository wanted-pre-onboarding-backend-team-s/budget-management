package com.saving.expense.service;

import com.saving.category.budget.domain.repository.BudgetRepository;
import com.saving.category.budget.dto.ResultFoundCategoryAndBudgetDto;
import com.saving.category.domain.entity.Category;
import com.saving.category.domain.repository.CategoryRepository;
import com.saving.category.exception.CategoryNotFoundException;
import com.saving.category.exception.MismatchedCategoryIdAndUserIdException;
import com.saving.expense.domain.entity.Expense;
import com.saving.expense.domain.repository.ExpenseRepository;
import com.saving.expense.dto.CategoryConsumeRate;
import com.saving.expense.dto.ExpenseListResponseDto;
import com.saving.expense.dto.ExpenseRequestDto;
import com.saving.expense.dto.ExpenseResponseDto;
import com.saving.expense.dto.ExpenseStatsResponseDto;
import com.saving.expense.dto.ResultExpenseRateComparedLastMonth;
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

    // 지출 기록
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

    // 지출 기록 수정
    @Transactional
    public void updateExpense(
            Long userId, Long expenseId, ExpenseRequestDto expenseRequestDto) {

        existUserAndCategory(expenseRequestDto.getCategoryId(), userId);

        Expense savedExpense = expenseRepository
                .findByIdAndCategoryId(expenseId, expenseRequestDto.getCategoryId())
                .orElseThrow(NotExistExpenseInCategoryException::new);

        savedExpense.changeExpense(expenseRequestDto);
    }

    // 지출 기록 삭제
    @Transactional
    public void deleteExpense(Long userId, Long expenseId) {

        if (!expenseRepository.existsByIdAndUserId(expenseId, userId)) {
            throw new NotExistExpenseInCategoryException();
        }
        expenseRepository.deleteById(expenseId);
    }

    // 지출 기록 상세
    @Transactional(readOnly = true)
    public ExpenseResponseDto getExpense(Long userId, Long expenseId) {

        return expenseRepository.findByIdAndUserId(expenseId, userId)
                .orElseThrow(NotExistExpenseInCategoryException::new);
    }

    // 지출 목록
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

    // 지출 통계
    public ExpenseStatsResponseDto expenseStats(Long userId) {

        LocalDate todayDate = LocalDate.now();
        YearMonth thisYearMonth = YearMonth.now(); // 해당 년, 월
        String thisMonthAtDayOne = todayDate.withDayOfMonth(1).toString(); // 이번달 1일 날짜
        String today = todayDate.toString(); // 오늘 날짜
        String lastMonthAtDayOne = thisYearMonth.atDay(1).toString(); // 저번달 1일 날짜
        String endOfLastMonth = thisYearMonth.atEndOfMonth().toString(); // 저번달 말일
        String lastDayOfWeek = todayDate.minusDays(7).toString(); // 지난 요일

        // 지난달 대비 총 소비율
        String resultTotalExpenseRateComparedLastMonth = getExpenseRateByTime(
                userId, thisMonthAtDayOne, today, lastMonthAtDayOne, endOfLastMonth);

        // 지난달 대비 카테고리별 소비율
        List<CategoryConsumeRate> resultExpenseRateCategoriesComparedLastMonth =
                getExpenseRateByCategoriesAndTime(userId, thisMonthAtDayOne, today,
                        lastMonthAtDayOne, endOfLastMonth);

        ResultExpenseRateComparedLastMonth resultExpenseRateComparedLastMonth =
                ResultExpenseRateComparedLastMonth.builder()
                .totalExpenseRateComparedLastMonth(resultTotalExpenseRateComparedLastMonth)
                .expenseRateCategoriesComparedLastMonth(resultExpenseRateCategoriesComparedLastMonth)
                .build();

        // 지난 요일 대비 소비율
        String resultExpenseRateComparedLastDayOfWeek =
                getExpenseRateByTime(userId, today, today, lastDayOfWeek, lastDayOfWeek);

        // 다른 유저 대비 나의 소비율
        String resultExpenseRateComparedOtherUsers = getMyExpenseRateComparedToOtherUsersByBudget(userId,
                thisYearMonth + "-01", thisMonthAtDayOne, today);

        return ExpenseStatsResponseDto
                .builder()
                .expenseRateComparedLastMonth(resultExpenseRateComparedLastMonth)
                .expenseRateComparedLastDayOfWeek(resultExpenseRateComparedLastDayOfWeek)
                .expenseRateComparedOtherUsers(resultExpenseRateComparedOtherUsers)
                .build();
    }

    // 나의 지출 안내 - 디스코드 웹훅
    @Scheduled(cron = "0 0 20 * * *", zone = "Asia/Seoul")
    public void sendDiscordWebhookTodayExpenseNoticeMessage() {
        log.debug("스케줄링 작업 시작");
        List<User> userList = userRepository.findByIsTodayExpenseNoticeIsTrue();

        userList.stream()
                .parallel()
                .forEach(this::sendAsyncWebhookMessage);
    }

    private List<CategoryConsumeRate> getExpenseRateByCategoriesAndTime(
            Long userId, String thisMonthAtDayOne, String today,
            String lastMonthAtDayOne, String endOfLastMonth) {

        List<CategoryConsumeRate> categoryConsumeRateList = new ArrayList<>();

        List<Category> categoryList = categoryRepository.findAllByUserId(userId);
        for (Category category : categoryList) {

            Long categoryId = category.getId();
            CategoryConsumeRate categoryConsumeRate = CategoryConsumeRate.builder()
                    .categoryId(categoryId)
                    .categoryName(category.getCategoryName())
                    .expenseRate(
                            getExpenseReteByCategoryAndTime(categoryId, thisMonthAtDayOne, today,
                            lastMonthAtDayOne, endOfLastMonth))
                    .build();
            categoryConsumeRateList.add(categoryConsumeRate);
        }
        return categoryConsumeRateList;
    }

    private String getExpenseRateByTime(Long userId, String startDate, String endDate,
            String startDateCompare, String endDateCompare) {

        Long compareExpense = expenseRepository.findSumOfExpenseByUserIdAndTime(
                        userId, startDate, endDate)
                .orElse(0L);
        if (compareExpense == 0) {
            return "0%";
        }

        Long comparedTargetExpense = expenseRepository.findSumOfExpenseByUserIdAndTime(
                        userId, startDateCompare, endDateCompare)
                .orElse(0L);
        if (comparedTargetExpense == 0) {
            return "100%";
        }

        return (int) ((double) compareExpense / comparedTargetExpense * 100) + "%";
    }

    private String getExpenseReteByCategoryAndTime(
            Long categoryId, String thisMonthAtDayOne, String today,
            String lastMonthAtDayOne, String endOfLastMonth) {

        Long thisMonthExpenseCategory =
                expenseRepository.findSumOfExpenseByCategoryIdAndTime(
                                categoryId, thisMonthAtDayOne, today)
                        .orElse(0L);
        if (thisMonthExpenseCategory == 0) {
            return "0%";
        }

        Long lastMonthExpenseCategory =
                expenseRepository.findSumOfExpenseByCategoryIdAndTime(
                                categoryId, lastMonthAtDayOne, endOfLastMonth)
                        .orElse(0L);
        if (lastMonthExpenseCategory == 0) {
            return "100%";
        }

        // 지난달 대비 이번달 카테고리 소비율
        return (int) ((double) thisMonthExpenseCategory / lastMonthExpenseCategory * 100)
                + "%";
    }

    private String getMyExpenseRateComparedToOtherUsersByBudget(
            Long userId, String thisYearMonth, String startDate, String endDate) {

        Long myBudget = budgetRepository.findSumAmountByUserId(userId,
                thisYearMonth).orElse(0L);
        if (myBudget == 0) {
            return "이번달 나의 예산이 없습니다.";
        }

        Long myExpense = expenseRepository.findSumAmountByUserIdAndTime(
                userId, startDate, endDate).orElse(0L);
        if (myExpense == 0) {
            return "이번달 나의 지출 기록이 없습니다.";
        }

        Long notMyBudget = budgetRepository.findSumAmountByNotUserId(userId,
                thisYearMonth).orElse(0L);
        if (notMyBudget == 0) {
            return "이번달 비교 대상이 없습니다.";
        }

        Long notMyExpense = expenseRepository.findSumAmountByNotUserIdAndTime(
                userId, startDate, endDate).orElse(0L);
        if (notMyExpense == 0) {
            return "이번달 비교 대상이 없습니다.";
        }

        return ((double) myExpense * notMyBudget) / (myBudget * notMyExpense) * 100 + "%";
    }

    private void existUserAndCategory(Long categoryId, Long userId) {
        if (!categoryRepository.existsByIdAndUserId(categoryId, userId)) {
            throw new MismatchedCategoryIdAndUserIdException();
        }
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
            Long categoryTodayExpenseSum = expenseRepository.findSumOfExpenseByCategoryIdAndTime(categoryId,
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
