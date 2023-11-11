package com.saving.user.dto;

import com.saving.user.domain.entity.User;
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

    @NotBlank(message = "필수 입력값 입니다.")
    @Size(min = 5, max = 20, message = "5~20자 이내로 입력해주세요.")
    private String username;

    @NotBlank(message = "필수 입력값 입니다.")
    @Size(min = 8, max = 16, message = "8~16자 이내로 입력해주세요.")
    private String password;

    @NotNull(message = "필수 입력값 입니다.")
    private int isTodayBudgetNotice;

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
