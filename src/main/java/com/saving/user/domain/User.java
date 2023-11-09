package com.saving.user.domain;

import com.saving.common.domain.entity.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Entity
@Getter
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private int isTodayBudgetNotice;
    private int isTodayExpenseNotice;

    @Builder
    public User(String username, String password, int isTodayBudgetNotice,
            int isTodayExpenseNotice) {
        this.username = username;
        this.password = password;
        this.isTodayBudgetNotice = isTodayBudgetNotice;
        this.isTodayExpenseNotice = isTodayExpenseNotice;
    }
}
