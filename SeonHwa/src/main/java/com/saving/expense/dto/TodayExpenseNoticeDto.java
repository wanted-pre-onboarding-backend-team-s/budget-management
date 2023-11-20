package com.saving.expense.dto;

import com.saving.expense.vo.CalcTodayCategoryExpenseVo;
import java.util.List;

public record TodayExpenseNoticeDto(
        Long todayTotalExpense,
        List<CalcTodayCategoryExpenseVo> statCategoryList) {

}
