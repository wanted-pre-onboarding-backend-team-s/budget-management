package com.saving.user.dto;

import com.saving.user.domain.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
public class UserCreateRequestDto {

    @Schema(title = "user name", description = "사용자 계정")
    @NotBlank(message = "필수 입력값 입니다.")
    @Size(min = 5, max = 20, message = "5~20자 이내로 입력해주세요.")
    private String username;

    @Schema(title = "user password", description = "사용자 비밀번호")
    @NotBlank(message = "필수 입력값 입니다.")
    @Size(min = 8, max = 16, message = "8~16자 이내로 입력해주세요.")
    private String password;

    @Schema(title = "is today budget notice", description = "오늘 지출 추천 알림 여부")
    @NotNull(message = "필수 입력값 입니다.")
    private int isTodayBudgetNotice;

    @Schema(title = "is today expense notice", description = "오늘 지출 안내 알림 여부")
    @NotNull(message = "필수 입력값 입니다.")
    private int isTodayExpenseNotice;

    @Builder
    public UserCreateRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void changPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    public User toEntity() {
        return User.builder()
                .username(this.username)
                .encodedPassword(this.password)
                .isTodayBudgetNotice(this.isTodayBudgetNotice)
                .isTodayExpenseNotice(this.isTodayExpenseNotice)
                .build();
    }
}
