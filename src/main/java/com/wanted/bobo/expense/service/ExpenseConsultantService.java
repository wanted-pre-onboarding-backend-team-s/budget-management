package com.wanted.bobo.expense.service;

import com.wanted.bobo.budget.domain.Budget;
import com.wanted.bobo.budget.domain.BudgetRepository;
import com.wanted.bobo.category.Category;
import com.wanted.bobo.expense.domain.Expense;
import com.wanted.bobo.expense.domain.ExpenseRepository;
import com.wanted.bobo.expense.dto.TodayExpenseRecMessage;
import com.wanted.bobo.expense.dto.TodayExpenseReportMessage;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class ExpenseConsultantService {

    @Value("${discord.webhook.url}")
    private String discordWebhookUrl;

    private final BudgetRepository budgetRepository;
    private final ExpenseRepository expenseRepository;

    @Scheduled(cron = "0 0 8 * * *")
    public void sendTodayExpenseRecommendation() {
        TodayExpenseRecMessage expenseRecMessage = generateExpenseRecommendations();

        WebClient webClient = WebClient.create();
        webClient.post()
                 .uri(discordWebhookUrl)
                 .body(BodyInserters.fromValue(expenseRecMessage.toWebhookMessage()))
                 .header("Content-Type", "application/json")
                 .retrieve()
                 .bodyToMono(String.class)
                 .subscribe();
    }

    private TodayExpenseRecMessage generateExpenseRecommendations() {
        List<Budget> budgets = budgetRepository.findByUserIdAndYearmonth(1L, YearMonth.now());
        List<Expense> expenses = expenseRepository.findByUserIdAndDate(
                1L,
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM")));

        int totalBudget = budgets.stream().mapToInt(Budget::getAmount).sum();
        int remainingBudget = totalBudget;

        Map<Category, Integer> remainingCategoryBudgets
                = budgets.stream()
                         .collect(Collectors.groupingBy(
                                 Budget::getCategory,
                                 Collectors.summingInt(Budget::getAmount)));

        for (Expense expense : expenses) {
            int amount = expense.getAmount();
            remainingBudget -= amount;

            if (remainingCategoryBudgets.containsKey(expense.getCategory())) {
                remainingCategoryBudgets.replace(
                        expense.getCategory(),
                        remainingCategoryBudgets.get(expense.getCategory()) - amount
                );
            }
        }

        return new TodayExpenseRecMessage(totalBudget, remainingBudget, remainingCategoryBudgets);
    }

    @Scheduled(cron = "0 0 20 * * *")
    public void sendTodayExpenseReport() {
        TodayExpenseReportMessage expenseRecMessage = new TodayExpenseReportMessage();

        WebClient webClient = WebClient.create();
        webClient.post()
                 .uri(discordWebhookUrl)
                 .body(BodyInserters.fromValue(expenseRecMessage.toWebhookMessage()))
                 .header("Content-Type", "application/json")
                 .retrieve()
                 .bodyToMono(String.class)
                 .subscribe();
    }

}

