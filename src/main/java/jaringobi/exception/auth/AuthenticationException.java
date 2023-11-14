package jaringobi.exception.auth;

import lombok.Getter;

@Getter
public class AuthenticationException extends RuntimeException {
    private final AuthenticationErrorType authenticationErrorType = AuthenticationErrorType.of(this.getClass());
}
