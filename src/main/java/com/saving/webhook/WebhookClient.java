package com.saving.webhook;

import com.saving.expense.dto.TodayExpenseNoticeDto;

public interface WebhookClient {

    void sendTodayExpenseNoticeMessage(
            String webhookUrl, String username, TodayExpenseNoticeDto todayExpenseNoticeDto);
}
