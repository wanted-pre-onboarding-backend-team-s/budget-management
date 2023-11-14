package com.wanted.bobo.expense.service;

import com.wanted.bobo.expense.dto.TodayExpenseRecMessage;
import com.wanted.bobo.expense.dto.TodayExpenseReportMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ExpenseConsultantService {

    @Value("${discord.webhook.url}")
    private String discordWebhookUrl;

    @Scheduled(cron = "0 0 8 * * *")
    public void sendTodayExpenseRecommendation() {
        TodayExpenseRecMessage expenseRecMessage = new TodayExpenseRecMessage();

        WebClient webClient = WebClient.create();
        webClient.post()
                 .uri(discordWebhookUrl)
                 .body(BodyInserters.fromValue(expenseRecMessage.toWebhookMessage()))
                 .header("Content-Type", "application/json")
                 .retrieve()
                 .bodyToMono(String.class)
                 .subscribe();
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
