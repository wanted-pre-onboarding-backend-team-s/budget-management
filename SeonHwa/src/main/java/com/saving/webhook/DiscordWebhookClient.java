package com.saving.webhook;

import static com.saving.webhook.WebhookConstants.*;

import com.saving.expense.dto.TodayExpenseNoticeDto;
import com.saving.expense.vo.CalcTodayCategoryExpenseVo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DiscordWebhookClient implements WebhookClient{

    @Override
    public void sendTodayExpenseNoticeMessage(
            String webhookUrl, String username, TodayExpenseNoticeDto todayExpenseNoticeDto) {

        WebClient baseWebClient = getBaseWebClient();
        postRequest(
                webhookUrl, createDiscordMessage(username, todayExpenseNoticeDto), baseWebClient);
    }

    private Map<String, Object> createDiscordMessage(
            String username, TodayExpenseNoticeDto todayExpenseNoticeDto) {

        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put(USERNAME, SERVICE_USERNAME);
        contentMap.put(CONTENT, SERVICE_CONTENT);

        List<Map<String, Object>> embeds = createEmbeds(username, todayExpenseNoticeDto);
        contentMap.put(EMBEDS, embeds);

        return contentMap;
    }

    private List<Map<String, Object>> createEmbeds(
            String username, TodayExpenseNoticeDto todayExpenseNoticeDto) {

        List<Map<String, Object>> embeds = new ArrayList<>();

        // embed1
        Map<String, Object> embed1 = new HashMap<>();
        embed1.put(TITLE, FIRST_TITLE);
        embed1.put(DESCRIPTION, username + FIRST_DESCRIPTION);

        List<Map<String, Object>> fields1 = new ArrayList<>();
        Map<String, Object> field = new HashMap<>();
        field.put(NAME, TODAY_EXPENSE);
        field.put(VALUE, todayExpenseNoticeDto.todayTotalExpense());
        fields1.add(field);
        embed1.put(FIELDS, fields1);

        embeds.add(embed1);

        // embed2
        Map<String, Object> embed2 = new HashMap<>();
        embed2.put(TITLE, SECOND_TITLE);
        embed2.put(DESCRIPTION, SECOND_DESCRIPTION);

        List<Map<String, Object>> fields2 = new ArrayList<>();
        for (CalcTodayCategoryExpenseVo calcTodayCategoryExpenseVo
                : todayExpenseNoticeDto.statCategoryList()) {

            fields2.add(createFieldMap(calcTodayCategoryExpenseVo));
        }
        embed2.put(FIELDS, fields2);

        embeds.add(embed2);

        return embeds;
    }

    private Map<String, Object> createFieldMap(
            CalcTodayCategoryExpenseVo calcTodayCategoryExpenseVo) {

        StringBuilder stringBuilder = new StringBuilder();
        String categoryName = calcTodayCategoryExpenseVo.getCategoryName();
        String dangerousDegreeTitle = calcTodayCategoryExpenseVo.getDangerousDegree().getTitle();
        String dangerousDegreeDescription =
                calcTodayCategoryExpenseVo.getDangerousDegree().getDescription();

        stringBuilder
                .append("[")
                .append(categoryName)
                .append("] ")
                .append(dangerousDegreeTitle);

        return Map.of(NAME, stringBuilder.toString(), VALUE, dangerousDegreeDescription);
    }

    private WebClient getBaseWebClient() {
        return WebClient.builder()
                .build();
    }

    private void postRequest(String webhookUrl, Map<String, Object> bodyMap,
            WebClient webClient) {
        webClient
                .post()
                .uri(webhookUrl)
                .bodyValue(bodyMap)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe();
    }
}
