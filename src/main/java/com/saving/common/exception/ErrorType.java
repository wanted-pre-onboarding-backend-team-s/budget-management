package com.saving.common.exception;

import com.saving.user.exception.DuplicateUserNameException;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    U001("U001", "계정을 찾을 수 없습니다.", CustomException.class, HttpStatus.BAD_REQUEST),
    U002("U002", "중복된 아이디 입니다.", DuplicateUserNameException.class, HttpStatus.BAD_REQUEST);

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
