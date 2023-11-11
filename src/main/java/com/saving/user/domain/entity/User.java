package com.saving.user.domain.entity;

import com.saving.common.domain.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Table(name = "users")
@Entity
@Getter
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(name = "password")
    private String encodedPassword;

    private int isTodayBudgetNotice;

    private int isTodayExpenseNotice;

    @Builder
    public User(String username, String encodedPassword,
            int isTodayBudgetNotice, int isTodayExpenseNotice) {

        this.username = username;
        this.encodedPassword = encodedPassword;
        this.isTodayBudgetNotice = isTodayBudgetNotice;
        this.isTodayExpenseNotice = isTodayExpenseNotice;
    }

    public boolean passwordMatches(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.matches(this.encodedPassword, password);
    }
}
