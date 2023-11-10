package jaringobi.exception;

import lombok.Getter;

@Getter
public class BudgetGlobalException extends RuntimeException{
    private final ErrorType errorType = ErrorType.of(this.getClass());
}
