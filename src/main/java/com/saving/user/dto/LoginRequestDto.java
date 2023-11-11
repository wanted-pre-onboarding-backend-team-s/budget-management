package com.saving.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = "필수 입력값 입니다.")
    @Size(min = 5, max = 20, message = "5~20자 이내로 입력해주세요.")
    private String username;

    @NotBlank(message = "필수 입력값 입니다.")
    @Size(min = 8, max = 16, message = "8~16자 이내로 입력해주세요.")
    private String password;

    @Builder
    public LoginRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
