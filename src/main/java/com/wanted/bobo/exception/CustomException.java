package com.wanted.bobo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {

    private final ErrorType errorType = ErrorType.of(this.getClass());
}