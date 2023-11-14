package jaringobi.exception;


import jaringobi.exception.budget.InvalidBudgetException;
import jaringobi.exception.budget.LowBudgetException;
import jaringobi.exception.jwt.InvalidTokenException;
import jaringobi.exception.user.NotFoundUserException;
import jaringobi.exception.user.PasswordNotMatchedException;
import jaringobi.exception.user.UsernameDuplicatedException;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    U001("U001", "이미 존재하는 계정명입니다.", UsernameDuplicatedException.class, HttpStatus.CONFLICT),
    U002("U002", "존재하지 않는 계정입니다.", NotFoundUserException.class, HttpStatus.NOT_FOUND),

    T001("T001", "유효하지 않은 토큰입니다.", InvalidTokenException.class, HttpStatus.BAD_REQUEST),

    B001("B001", "올바르지 않은 예산 정보입니다.", InvalidBudgetException.class, HttpStatus.BAD_REQUEST),
    B002("B002", "지정 예산은 0원을 넘을 수 없습니다.", LowBudgetException.class, HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final Class<? extends BudgetGlobalException> classType;
    private final HttpStatus httpStatus;
    private static final List<ErrorType> errorTypes = Arrays.stream(ErrorType.values()).toList();

    public static ErrorType of(Class<? extends BudgetGlobalException> classType) {
        return errorTypes.stream()
                .filter(it -> it.classType.equals(classType))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
