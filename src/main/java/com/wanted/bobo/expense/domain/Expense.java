package com.wanted.bobo.expense.domain;

import com.wanted.bobo.category.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int amount;
    private Long userId;
    private String memo;
    private boolean isExcepted;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Builder
    public Expense(Long userId, Category category, int amount, String memo, LocalDate date) {
        this.userId = userId;
        this.amount = amount;
        this.category = category;
        this.memo = memo;
        this.date = date;
        this.isExcepted = false;
    }
}
