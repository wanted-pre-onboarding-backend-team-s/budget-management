package com.wanted.bobo.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.wanted.bobo.user.domain.User;
import com.wanted.bobo.user.domain.UserRepository;
import com.wanted.bobo.user.dto.JoinRequest;
import com.wanted.bobo.user.dto.JoinResponse;
import com.wanted.bobo.user.exception.DuplicateUsernameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class JoinServiceTest {

    private static final String REQUEST_USERNAME = "test";
    private static final String REQUEST_PASSWORD = "test1234";

    @InjectMocks
    private JoinService joinService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @DisplayName("회원가입 성공")
    @Test
    void save_join_success() {
        JoinRequest request = new JoinRequest(REQUEST_USERNAME, REQUEST_PASSWORD);
        String encryptedPassword = passwordEncoder.encode(request.getPassword());

        when(userRepository.save(any())).thenReturn(
                new User(request.getUsername(), encryptedPassword));
        JoinResponse response = joinService.join(request);

        assertThat(response.getUsername()).isEqualTo(request.getUsername());

    }

    @DisplayName("중복된 계정이면 회원가입 실패")
    @Test
    void duplicate_username_join_fail() {
        JoinRequest request = new JoinRequest(REQUEST_USERNAME, REQUEST_PASSWORD);
        when(userRepository.existsByUsername(any())).thenReturn(true);

        assertThatThrownBy(() -> joinService.join(request)).isInstanceOf(DuplicateUsernameException.class);
    }
}