package jaringobi.auth;

import jakarta.servlet.http.HttpServletRequest;
import jaringobi.domain.user.AppUser;
import jaringobi.exception.auth.AccessTokenExpiredException;
import jaringobi.exception.auth.InvalidTokenException;
import jaringobi.jwt.TokenProvider;

public class AuthenticationTokenService implements AuthenticationService {

    private final TokenProvider tokenProvider;

    public AuthenticationTokenService(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void checkAuthentication(HttpServletRequest request) {
        String token = tokenProvider.parseTokenFromHeader(request);
        if (!tokenProvider.isValidToken(token)) {
            throw new InvalidTokenException();
        }
        if (tokenProvider.isExpired(token)) {
            throw new AccessTokenExpiredException();
        }
    }

    @Override
    public AppUser findUserByToken(String token) {
        Long userId = tokenProvider.parseUserId(token);
        return new AppUser(userId);
    }
}
