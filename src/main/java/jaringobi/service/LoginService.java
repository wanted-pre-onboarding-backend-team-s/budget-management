package jaringobi.service;

import jaringobi.controller.request.LoginRequest;
import jaringobi.controller.response.LoginResponse;
import jaringobi.domain.user.User;
import jaringobi.domain.user.UserRepository;
import jaringobi.exception.user.NotFoundUserException;
import jaringobi.exception.user.PasswordNotMatchedException;
import jaringobi.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public LoginResponse login(LoginRequest loginRequest) {
        User user = findUser(loginRequest);

        if (!user.matchesPassword(passwordEncoder, loginRequest.getPassword())) {
            throw new PasswordNotMatchedException();
        }

        String accessToken = tokenProvider.issueAccessToken(user.getId());
        String refreshToken = tokenProvider.issueRefreshToken(user.getId());
        return LoginResponse.from(accessToken, refreshToken);
    }

    private User findUser(LoginRequest loginRequest) {
        return userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(NotFoundUserException::new);
    }
}
