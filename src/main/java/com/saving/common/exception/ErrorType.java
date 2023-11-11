package com.saving.common.exception;

import com.saving.category.budget.exception.BudgetForCategoryAlreadyExistException;
import com.saving.category.budget.exception.BudgetNotFoundException;
import com.saving.category.exception.CategoryNotFoundException;
import com.saving.expense.exception.ExpenseMethodNotFoundException;
import com.saving.expense.exception.ExpenseNotFoundException;
import com.saving.user.exception.DuplicateUserNameException;
import com.saving.user.exception.InvalidTokenException;
import com.saving.user.exception.JwtExpiredException;
import com.saving.user.exception.NullTokenException;
import com.saving.user.exception.UserNotFoundException;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    U001("U001", "존재하지 않는 계정입니다.",
            UserNotFoundException.class, HttpStatus.NOT_FOUND),
    U002("U002", "중복된 아이디 입니다.",
            DuplicateUserNameException.class, HttpStatus.BAD_REQUEST),

    T001("T001", "토큰을 입력해주세요.",
            NullTokenException.class, HttpStatus.BAD_REQUEST),
    T002("T002", "유효하지 않은 토큰입니다.",
            InvalidTokenException.class, HttpStatus.BAD_REQUEST),
    T003("T003", "만료된 토큰입니다.",
            JwtExpiredException.class, HttpStatus.BAD_REQUEST),

    C001("C001", "존재하지 않는 카테고리입니다.",
            CategoryNotFoundException.class, HttpStatus.NOT_FOUND),

    B001("B001", "이미 존재하는 카테고리의 예산입니다.",
            BudgetForCategoryAlreadyExistException.class, HttpStatus.BAD_REQUEST),
    B002("B002", "존재하지 않는 예산입니다."
            , BudgetNotFoundException.class, HttpStatus.NOT_FOUND),

    EP001("EP001", "존재하지 않는 화폐유형입니다.",
            ExpenseMethodNotFoundException.class, HttpStatus.NOT_FOUND),
    EP002("EP002", "존재하지 않는 지출 내역입니다.",
            ExpenseNotFoundException.class, HttpStatus.NOT_FOUND);

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
