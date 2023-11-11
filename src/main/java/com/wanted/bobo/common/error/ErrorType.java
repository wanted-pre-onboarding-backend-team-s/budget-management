package com.wanted.bobo.common.error;

import com.wanted.bobo.common.jwt.exception.ExpiredTokenException;
import com.wanted.bobo.common.jwt.exception.MissingRequestHeaderAuthorizationException;
import com.wanted.bobo.user.exception.DuplicateUsernameException;

import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    U001("U001", "중복된 계정입니다.", DuplicateUsernameException.class, HttpStatus.CONFLICT),

    T001("T001", "헤더에 토큰이 존재하지 않습니다.", MissingRequestHeaderAuthorizationException.class, HttpStatus.UNAUTHORIZED),
    T002("T002", "만료된 토큰입니다.", ExpiredTokenException.class, HttpStatus.UNAUTHORIZED);

    private final String code;
    private final String message;
    private final Class<? extends CustomException> classType;
    private final HttpStatus httpStatus;
    private static final List<ErrorType> errorTypes = Arrays.stream(ErrorType.values()).toList();

    public static ErrorType of(Class<? extends CustomException> classType) {
        return errorTypes.stream()
                         .filter(it -> it.classType.equals(classType))
                         .findFirst()
                         .orElseThrow(RuntimeException::new);
    }
}
