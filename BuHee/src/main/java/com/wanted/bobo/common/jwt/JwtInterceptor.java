package com.wanted.bobo.common.jwt;

import com.wanted.bobo.common.jwt.exception.MissingRequestHeaderAuthorizationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorization = request.getHeader(JwtProperties.HEADER_STRING);

        if(authorization == null) {
            throw new MissingRequestHeaderAuthorizationException();
        }

        String token = authorization.replaceAll(JwtProperties.TOKEN_PREFIX, "");

        if(jwtProvider.validateToken(token)) {
            Long userId = jwtProvider.getUserId(token);
            request.setAttribute("userId", userId);

            return true;
        }

        return false;
    }
}
