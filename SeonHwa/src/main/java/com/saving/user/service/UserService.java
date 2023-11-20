package com.saving.user.service;

import com.saving.common.response.JwtResponse;
import com.saving.common.util.JwtUtil;
import com.saving.user.domain.entity.User;
import com.saving.user.domain.repository.UserRepository;
import com.saving.user.dto.LoginRequestDto;
import com.saving.user.dto.UserCreateRequestDto;
import com.saving.user.dto.UserCreatedResponseDto;
import com.saving.user.exception.DuplicateUserNameException;
import com.saving.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public UserCreatedResponseDto createUser(UserCreateRequestDto userCreateRequestDto) {

        if (userRepository.existsByUsername(userCreateRequestDto.getUsername())) {
            throw new DuplicateUserNameException();
        }
        userCreateRequestDto.changPassword(passwordEncoder);

        return new UserCreatedResponseDto(userRepository.save(userCreateRequestDto.toEntity()));
    }

    @Transactional(readOnly = true)
    public JwtResponse authenticationAndCreateJwt(LoginRequestDto loginRequestDto) {

        User getUser = userRepository
                .findByUsername(loginRequestDto.getUsername())
                .orElseThrow(UserNotFoundException::new);

        if (getUser.passwordMatches(passwordEncoder, loginRequestDto.getPassword())) {
            return jwtUtil.createJwt(getUser.getId());
        }

        throw new UserNotFoundException();
    }
}
