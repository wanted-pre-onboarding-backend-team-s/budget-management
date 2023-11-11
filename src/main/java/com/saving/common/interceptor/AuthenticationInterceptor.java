package com.saving.common.interceptor;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.saving.common.util.JwtUtil;
import com.saving.user.exception.InvalidTokenException;
import com.saving.user.exception.JwtExpiredException;
import com.saving.user.exception.NullTokenException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final String FIRST_STRING_OF_TOKEN = "Bearer";
    private static final String AUTHORIZATION = "Authorization";

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {

        String authorization = request.getHeader(AUTHORIZATION);

        if (authorization == null) {
            log.error("[NullTokenException] ex");
            throw new NullTokenException();
        }

        if (!authorization.startsWith(FIRST_STRING_OF_TOKEN)) {
            log.error("[InvalidTypeOfTokenException] ex");
            throw new InvalidTokenException();
        }

        String token = authorization.split(" ")[1];

        try {
            request.setAttribute("userId", jwtUtil.verifyToken(token));
            return true;

        } catch (TokenExpiredException e) {
            log.error("[TokenExpiredException] ex", e);
            throw new JwtExpiredException();

        } catch (Exception e) {
            log.error("[{}] ex ", e.getClass().getSimpleName(), e);
            throw new InvalidTokenException();
        }
    }
}
