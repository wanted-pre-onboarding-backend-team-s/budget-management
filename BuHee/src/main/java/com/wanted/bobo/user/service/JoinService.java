package com.wanted.bobo.user.service;

import com.wanted.bobo.user.domain.User;
import com.wanted.bobo.user.domain.UserRepository;
import com.wanted.bobo.user.dto.JoinRequest;
import com.wanted.bobo.user.dto.JoinResponse;
import com.wanted.bobo.user.exception.DuplicateUsernameException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public JoinResponse join(JoinRequest request) {
        validateDuplicateUsername(request.getUsername());

        User user = userRepository.save(request.toEntity());
        user.changePassword(passwordEncoder.encode(request.getPassword()));

        return new JoinResponse(user.getId(), user.getUsername());
    }

    private void validateDuplicateUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new DuplicateUsernameException();
        }
    }

}
