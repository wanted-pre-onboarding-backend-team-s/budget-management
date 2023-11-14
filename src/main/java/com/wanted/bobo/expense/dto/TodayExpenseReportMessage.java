package com.wanted.bobo.expense.dto;

import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TodayExpenseReportMessage {

    private String content;

    public Map<String, Object> toWebhookMessage() {
        MessageBuilder messageBuilder = new MessageBuilder("오늘의 지출 안내");
        return messageBuilder.build();
    }
}
