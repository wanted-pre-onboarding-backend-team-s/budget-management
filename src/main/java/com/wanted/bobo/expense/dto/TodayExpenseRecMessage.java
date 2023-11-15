package com.wanted.bobo.expense.dto;

import com.wanted.bobo.category.Category;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TodayExpenseRecMessage {

    int totalBudget;
    int remainingBudget;
    Map<Category, Integer> dailyRecommendedCategoryBudgets;

    public TodayExpenseRecMessage(
            int totalBudget,
            int remainingBudget,
            Map<Category, Integer> dailyRecommendedCategoryBudgets) {
        this.totalBudget = totalBudget;
        this.remainingBudget = remainingBudget;
        this.dailyRecommendedCategoryBudgets = dailyRecommendedCategoryBudgets;
    }

    public Map<String, Object> toWebhookMessage() {
        MessageBuilder messageBuilder = new MessageBuilder("오늘의 지출 추천  📢", buildDescription());
        return messageBuilder.build();
    }

    private String buildDescription() {
        StringBuilder description = new StringBuilder();

        if (remainingBudget > totalBudget * 0.8) {
            description.append("\n💡ㅤ지금까지 절약을 잘 실천하고 계세요! 오늘도 절약 도전!ㅤ💡\n\n");
        } else if (remainingBudget > totalBudget * 0.6) {
            description.append("\n👏ㅤ지금까지 적당히 사용 중이시군요! 계속 이렇게 유지하세요.ㅤ👏\n\n");
        } else if (remainingBudget > totalBudget * 0.2) {
            description.append("\n⚠️ㅤ지금까지 예산의 절반 이상 사용되었습니다. 소비를 다시 한번 체크해보세요.ㅤ⚠️\n\n");
        } else if (remainingBudget > totalBudget) {
            description.append("\n🚨ㅤ예산을 초과했습니다! 조금 더 절약해보세요!ㅤ🚨\n\n");
        }

        String startComment = "오늘도 예산 내에서 효율적으로 소비해보세요!ㅤ\n\n";
        description.append(startComment);

        boolean budgetAlert = false;

        int daysRemainingInMonth = YearMonth.now().lengthOfMonth() - LocalDate.now().getDayOfMonth() + 1;
        int dailyBudget = remainingBudget / daysRemainingInMonth;

        description.append("오늘 사용 가능한 총 금액은 ")
                   .append(floorTo100(dailyBudget))
                   .append("원 입니다.\n\n");

        description.append("오늘 카테고리 별 사용 가능한 금액은 아래와 같습니다.\n");
        for (Map.Entry<Category, Integer> entry : dailyRecommendedCategoryBudgets.entrySet()) {
            Category category = entry.getKey();
            int remainingBudget = entry.getValue();
            int minimumBudget = 100000;

            if (remainingBudget > 0) {
                description.append(category.getName())
                           .append("ㅤ")
                           .append(floorTo100(remainingBudget))
                           .append("원ㅤ");
            } else {
                description.append(category.getName())
                           .append("(최소금액)ㅤ")
                           .append(minimumBudget)
                           .append("원ㅤ");

                budgetAlert = true;
            }
        }

        if (budgetAlert) {
            description.append("\n\n⚠️ㅤ예산을 초과하거나 모두 사용한 카테고리가 있습니다. 유의하세요!ㅤ⚠️\n");
        } else {
            description.append("\n\n모든 카테고리의 예산이 정상입니다.\n");
        }

        return description.toString();
    }

    private static int floorTo100(double amount) {
        return (int) (Math.floor(amount / 100) * 100);
    }

}