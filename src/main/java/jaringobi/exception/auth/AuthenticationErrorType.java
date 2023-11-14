package jaringobi.exception.auth;

import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthenticationErrorType {

    EXPIRED_AT("AUTH_01", "엑세스 토큰이 만료되었습니다.", AccessTokenExpiredException.class),
    INVALID_TOKEN("AUTH_02", "유효하지 않은 토큰입니다.", InvalidTokenException.class);

    private final String code;
    private final String message;
    private final Class<? extends AuthenticationException> classType;
    private static final List<AuthenticationErrorType> errorTypes = Arrays.stream(AuthenticationErrorType.values()).toList();

    public static AuthenticationErrorType of(Class<? extends AuthenticationException> classType) {
        return errorTypes.stream()
                .filter(it -> it.getClassType().equals(classType))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
