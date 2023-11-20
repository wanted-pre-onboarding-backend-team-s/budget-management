package com.saving.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorType errorType = ErrorType.of(this.getClass());
}
