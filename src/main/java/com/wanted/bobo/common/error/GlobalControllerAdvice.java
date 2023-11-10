package com.wanted.bobo.common.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleWantedException(CustomException exception) {
        ErrorType errorType = exception.getErrorType();

        log.error("[error] type: {}, status: {}, message: {}",
                  errorType.getClassType().getSimpleName(),
                  errorType.getHttpStatus(),
                  errorType.getMessage());

        return ResponseEntity
                .status(errorType.getHttpStatus())
                .body(ErrorResponse.of(errorType));
    }

}