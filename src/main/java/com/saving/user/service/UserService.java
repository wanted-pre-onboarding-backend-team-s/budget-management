package com.saving.user.service;

import com.saving.user.domain.repository.UserRepository;
import com.saving.user.dto.UserCreateRequestDto;
import com.saving.user.dto.UserCreatedResponseDto;
import com.saving.user.exception.DuplicateUserNameException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserCreatedResponseDto createUser(UserCreateRequestDto userCreateRequestDto) {

        if (userRepository.existsByUsername(userCreateRequestDto.getUsername())) {
            throw new DuplicateUserNameException();
        }
        userCreateRequestDto.changPassword(passwordEncoder);

        return new UserCreatedResponseDto(userRepository.save(userCreateRequestDto.toEntity()));
    }
}
