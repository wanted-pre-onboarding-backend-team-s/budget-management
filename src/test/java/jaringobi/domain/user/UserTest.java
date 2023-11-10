package jaringobi.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jaringobi.common.config.SecurityConfig;
import java.lang.reflect.Field;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserTest {

    private final SecurityConfig securityConfig = new SecurityConfig();
    private final PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();

    @Test
    @DisplayName("성공 - Builder 로 유저 객체 생성")
    void successCreateUserWithBuilder() {
        assertThatCode(() -> User.builder()
                .username("username")
                .password("password")
                .build()).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("성공 - 패스워드 암호화")
    void successPasswordEncrypt() throws NoSuchFieldException, IllegalAccessException {
        String rawPassword = "password123!@#";
        User user = User.builder()
                .username("username123")
                .password(rawPassword)
                .build();

        assertDoesNotThrow(() -> user.encryptPassword(passwordEncoder));
        String encryptedPassword = getPassword(user);
        assertThat(encryptedPassword).isNotEqualTo(rawPassword);
    }

    private String getPassword(User user) throws NoSuchFieldException, IllegalAccessException {
        Field passwordField = User.class.getDeclaredField("password");
        passwordField.setAccessible(true);
        return (String) passwordField.get(user);
    }

    @Nested
    @DisplayName("실패 - Builder 로 유저 객체 생성")
    class FailUserTest {

        @DisplayName("username 이 null 이거나 empty 일 경우 예외를 던진다.")
        @ParameterizedTest
        @ValueSource(strings = {"", "   "})
        @NullSource
        void userNameCantBeNullOrEmpty(String username) {
            assertThrows(IllegalArgumentException.class, () -> User.builder()
                    .username(username)
                    .password("password")
                    .build(), "username must not be empty");
        }

        @DisplayName("password 가 null 이거나 empty 일 경우 예외를 던진다.")
        @ParameterizedTest
        @ValueSource(strings = {"", "   "})
        @NullSource
        void passwordCantBeNullOrEmpty(String password) {
            assertThrows(IllegalArgumentException.class, () -> User.builder()
                    .username("username")
                    .password(password)
                    .build(), "username must not be empty");
        }
    }

}