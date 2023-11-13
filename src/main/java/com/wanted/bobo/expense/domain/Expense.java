package com.wanted.bobo.expense.domain;

import com.wanted.bobo.category.Category;
import com.wanted.bobo.expense.dto.ExpenseRequest;
import com.wanted.bobo.expense.exception.NotMatchExpenseUserException;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private boolean isExclude;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private Category category;

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Builder
    public Expense(Long userId, Category category, int amount, String memo, LocalDate date, boolean isExclude) {
        this.userId = userId;
        this.amount = amount;
        this.category = category;
        this.memo = memo;
        this.date = date;
        this.isExclude = isExclude;
    }

    public void verifyMatchUser(Long userId) {
        if (!this.userId.equals(userId)) {
            throw new NotMatchExpenseUserException();
        }
    }

    public void changeInfo(ExpenseRequest request) {
        this.amount = request.getAmount();
        this.category = Category.of(request.getCategory());
        this.memo = request.getMemo();
        this.date = LocalDate.parse(request.getDate(), DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public void toggleExclude() {
        this.isExclude = !isExclude;
    }
}
