package com.saving.expense.vo;

import static com.saving.expense.domain.enums.DangerDegree.DANGER;
import static com.saving.expense.domain.enums.DangerDegree.NICE;
import static com.saving.expense.domain.enums.DangerDegree.WARNING;
import static com.saving.expense.domain.enums.DangerDegree.WELL;

import com.saving.expense.domain.enums.DangerDegree;
import java.time.LocalDate;
import java.time.YearMonth;
import lombok.Getter;

@Getter
public class CalcTodayCategoryExpenseVo {

    private String categoryName; // 카테고리 이름
    private int reasonableAmountExpenseToday; // 오늘의 카테고리 적정 지출 금액
    private int todayExpense; // 오늘의 카테고리 지출 금액
    private DangerDegree dangerousDegree; // 위험도

    public CalcTodayCategoryExpenseVo(String categoryName, int categoryBudget, Long categoryTotalExpenseUpToYesterday, int todayExpense) {

        this.categoryName = categoryName;

        // 카테고리 일 예산 계산
        double todayCategoryBudget = calcTodayCategoryBudget(categoryBudget);

        this.reasonableAmountExpenseToday = calcReasonableAmountExpenseToday(
                todayCategoryBudget, categoryTotalExpenseUpToYesterday);

        this.todayExpense = todayExpense;

        setDangerousDegree(todayExpense);
    }

    private int calcReasonableAmountExpenseToday(double todayCategoryBudget, Long categoryTotalExpenseUpToYesterday) {
        int today = LocalDate.now().getDayOfMonth();
        return (int) (todayCategoryBudget * today - categoryTotalExpenseUpToYesterday);
    }

    private double calcTodayCategoryBudget(int categoryBudget) {
        YearMonth todayYearMonth = YearMonth.now();
        int lastDayOfTheMonth = Integer.parseInt(todayYearMonth.atEndOfMonth().toString().substring(8));
        return (double) categoryBudget/lastDayOfTheMonth;
    }

    private void setDangerousDegree(int todayExpense) {

        int difference = (int) (todayExpense / this.reasonableAmountExpenseToday * 100.0);

        if (todayExpense < 0) {
            this.dangerousDegree = DANGER;
        }

        if (difference > 100) {
            this.dangerousDegree = WARNING;
        }

        if (difference >= 80 && difference <= 100) {
            this.dangerousDegree = WELL;
        }

        if (difference < 80) {
            this.dangerousDegree = NICE;
        }
    }
}
