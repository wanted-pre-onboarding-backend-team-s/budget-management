package com.saving.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDto {

    @Schema(title = "user name", description = "사용자 계정")
    @NotBlank(message = "필수 입력값 입니다.")
    @Size(min = 5, max = 20, message = "5~20자 이내로 입력해주세요.")
    private String username;

    @Schema(title = "user password", description = "사용자 비밀번호")
    @NotBlank(message = "필수 입력값 입니다.")
    @Size(min = 8, max = 16, message = "8~16자 이내로 입력해주세요.")
    private String password;

    @Builder
    public LoginRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
