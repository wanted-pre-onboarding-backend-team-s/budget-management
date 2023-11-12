package com.wanted.bobo.common.error;

import com.wanted.bobo.budget.exception.DuplicateBudgetCategoryException;
import com.wanted.bobo.budget.exception.NotFoundBudgetException;
import com.wanted.bobo.budget.exception.NotMatchUserException;
import com.wanted.bobo.common.jwt.exception.ExpiredTokenException;
import com.wanted.bobo.common.jwt.exception.MissingRequestHeaderAuthorizationException;
import com.wanted.bobo.user.exception.DuplicateUsernameException;
import com.wanted.bobo.user.exception.MismatchedPasswordException;
import com.wanted.bobo.user.exception.NotFoundUsernameException;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    U001("U001", "중복된 계정입니다.", DuplicateUsernameException.class, HttpStatus.CONFLICT),
    U002("U002", "존재하지 않는 계정입니다.", NotFoundUsernameException.class, HttpStatus.NOT_FOUND),
    U003("U003", "비밀번호가 일치하지 않습니다.", MismatchedPasswordException.class, HttpStatus.NOT_FOUND),
    B003("U004", "수정(삭제) 권한이 없습니다.", NotMatchUserException.class, HttpStatus.FORBIDDEN),

    T001("T001", "헤더에 토큰이 존재하지 않습니다.", MissingRequestHeaderAuthorizationException.class, HttpStatus.UNAUTHORIZED),
    T002("T002", "만료된 토큰입니다.", ExpiredTokenException.class, HttpStatus.UNAUTHORIZED),

    B001("B001", "이미 등록된 예산 카테고리 입니다.", DuplicateBudgetCategoryException.class, HttpStatus.CONFLICT),
    B002("B002", "존재하지 않는 예산 정보 입니다.", NotFoundBudgetException.class, HttpStatus.NOT_FOUND);

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
