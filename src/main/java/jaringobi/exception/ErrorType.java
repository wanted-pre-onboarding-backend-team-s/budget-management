package jaringobi.exception;


import jaringobi.exception.user.UsernameDuplicatedException;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    U001("U001", "이미 존재하는 계정명입니다.", UsernameDuplicatedException.class, HttpStatus.CONFLICT);

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
