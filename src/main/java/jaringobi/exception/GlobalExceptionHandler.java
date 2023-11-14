package jaringobi.exception;

import jaringobi.exception.auth.AuthenticationErrorType;
import jaringobi.exception.auth.AuthenticationException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BudgetGlobalException.class)
    public ResponseEntity<ErrorResponse> handleWantedException(BudgetGlobalException e) {
        ErrorType errorType = e.getErrorType();
        log.info("[error] type: {}, sts: {}, msg: {}", errorType.getClassType().getSimpleName(),
                errorType.getHttpStatus(), errorType.getMessage());
        return ResponseEntity.status(e.getErrorType().getHttpStatus()).body(ErrorResponse.of(e.getErrorType()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<BindingErrorField> bindingErrorFields = e.getFieldErrors()
                .stream()
                .map(BindingErrorField::of)
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of("E001", bindingErrorFields));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.of("E001", "잘못된 요청값입니다."));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e) {
        AuthenticationErrorType authErrorType = e.getAuthenticationErrorType();
        log.info("[auth error] type : {}, msg: {}", authErrorType.getClassType(), authErrorType.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorResponse.of(authErrorType.getCode(), authErrorType.getMessage()));
    }
}
