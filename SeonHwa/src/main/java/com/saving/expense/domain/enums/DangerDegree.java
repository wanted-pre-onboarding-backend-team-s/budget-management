package com.saving.expense.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DangerDegree {

    NICE("의 지출 날씨는 **아주 맑음** 입니다.", "굉장합니다! 오늘 지출을 굉장히 절약하셨군요! 👏 내일도 맑은 지출 날씨를 유지해보아요! 😊"),
    WELL("의 지출 날씨는 **맑음** 입니다.", "오늘의 적정 지출 금액에 딱 맞게 사용하셨군요! 😊 내일도 맑은 지출 날씨를 유지해보아요! 😊"),
    WARNING("의 지출 날씨는 **흐림** 입니다.", "오늘의 적정 지출 금액을 초과하였네요! 😮 우리 내일은 맑은 지출 날씨를 위해 노력해볼까요? 😊"),
    DANGER("의 지출 날씨는 **비** 입니다.", "현재까지의 예산 금액이 초과된 상태에요! 😢 우리 남은 기간은 지출을 조금 더 아껴서 맑은 지출 날씨를 위해 노력해보아요! 😊");

    private final String title;
    private final String description;
}
