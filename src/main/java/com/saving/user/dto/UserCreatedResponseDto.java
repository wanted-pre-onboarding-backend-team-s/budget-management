package com.saving.user.dto;

import com.saving.user.domain.entity.User;
import lombok.Getter;

@Getter
public class UserCreatedResponseDto{
    private final Long id;
    private final String username;
    private final int isTodayBudgetNotice;
    private final int isTodayExpenseNotice;

    public UserCreatedResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.isTodayBudgetNotice = user.getIsTodayBudgetNotice();
        this.isTodayExpenseNotice = user.getIsTodayExpenseNotice();
    }
}
