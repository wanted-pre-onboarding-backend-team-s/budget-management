package com.saving.common.config.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import com.saving.common.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expired-ms}")
    private Long expiredMs;

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil(Algorithm.HMAC256(jwtSecret), expiredMs);
    }
}
