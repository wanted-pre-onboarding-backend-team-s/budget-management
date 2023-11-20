package com.saving.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.saving.common.response.JwtResponse;
import java.util.Date;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtUtil {

    private static final String CLAIM_USER_ID = "userId";

    private final Algorithm algorithm;
    private final Long expiredMs;

    public JwtResponse createJwt(Long userId) {

        Date expiredTime = new Date(System.currentTimeMillis() + expiredMs);

        return JwtResponse.builder()
                .token(JWT.create()
                        .withClaim(CLAIM_USER_ID, userId)
                        .withExpiresAt(expiredTime)
                        .sign(algorithm))
                .expiredTime(expiredTime.toString())
                .build();
    }

    public Long verifyToken(String token) {

        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT verified = verifier.verify(token);

        return verified.getClaim(CLAIM_USER_ID).asLong();
    }
}
