package com.wanted.bobo.user.domain;

import com.wanted.bobo.user.exception.MismatchedPasswordException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String url;

    @Builder
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void checkPasswordMatches(String password, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(password, this.getPassword())) {
            throw new MismatchedPasswordException();
        }
    }

}
