package com.saving.user.dto;

import com.saving.user.domain.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserCreatedResponseDto{

    @Schema(title = "user id", description = "사용자 아이디")
    private final Long id;

    @Schema(title = "user name", description = "사용자 계정")
    private final String username;

    @Schema(title = "is today budget notice", description = "오늘 지출 추천 알림 여부")
    private final int isTodayBudgetNotice;

    @Schema(title = "is today expense notice", description = "오늘 지출 안내 알림 여부")
    private final int isTodayExpenseNotice;

    public UserCreatedResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.isTodayBudgetNotice = user.getIsTodayBudgetNotice();
        this.isTodayExpenseNotice = user.getIsTodayExpenseNotice();
    }
}
