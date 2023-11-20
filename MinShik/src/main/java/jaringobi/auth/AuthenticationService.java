package jaringobi.auth;

import jakarta.servlet.http.HttpServletRequest;
import jaringobi.domain.user.AppUser;

public interface AuthenticationService {

    void checkAuthentication(HttpServletRequest request);

    AppUser findUserByToken(String token);
}
