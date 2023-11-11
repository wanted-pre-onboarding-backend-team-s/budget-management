package com.saving.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JwtResponse {

    private String token;

    private String expiredTime;

    @Builder
    public JwtResponse(String token, String expiredTime) {
        this.token = token;
        this.expiredTime = expiredTime;
    }
}
