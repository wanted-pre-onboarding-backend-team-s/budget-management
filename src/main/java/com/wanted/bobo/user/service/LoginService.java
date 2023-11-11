package com.wanted.bobo.user.service;

import com.wanted.bobo.common.jwt.JwtProvider;
import com.wanted.bobo.user.domain.User;
import com.wanted.bobo.user.domain.UserRepository;
import com.wanted.bobo.user.dto.LoginRequest;
import com.wanted.bobo.user.dto.LoginResponse;
import com.wanted.bobo.user.exception.NotFoundUsernameException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                                  .orElseThrow(NotFoundUsernameException::new);

        user.checkPasswordMatches(request.getPassword(), passwordEncoder);

        return LoginResponse.toResponse(jwtProvider.generateToken(user));
    }

}
