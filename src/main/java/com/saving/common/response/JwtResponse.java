package com.saving.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JwtResponse {

    @Schema(title = "token", description = "발급된 토큰")
    private String token;

    @Schema(title = "token expired time", description = "토큰 만료일")
    private String expiredTime;

    @Builder
    public JwtResponse(String token, String expiredTime) {
        this.token = token;
        this.expiredTime = expiredTime;
    }
}
