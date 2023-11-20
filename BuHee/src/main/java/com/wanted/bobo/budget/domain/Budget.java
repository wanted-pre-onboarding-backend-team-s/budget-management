package com.wanted.bobo.budget.domain;

import com.wanted.bobo.budget.dto.BudgetRequest;
import com.wanted.bobo.budget.exception.NotMatchBudgetUserException;
import com.wanted.bobo.category.Category;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int amount;
    private Long userId;

    @Convert(converter = YearMonthAttributeConverter.class)
    private YearMonth yearmonth;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Builder
    public Budget(int amount, Long userId, Category category, YearMonth yearMonth) {
        this.amount = amount;
        this.userId = userId;
        this.category = category;
        this.yearmonth = yearMonth;
    }

    public void verifyMatchUser(Long userId) {
        if(!this.userId.equals(userId)) {
            throw new NotMatchBudgetUserException();
        }
    }
    public boolean verifyEqualCategory(Category category) {
        return this.category.equals(category);
    }

    public void changeInfo(BudgetRequest request) {
        this.amount = request.getAmount();
        this.category = Category.of(request.getCategory());
        this.yearmonth = YearMonth.parse(request.getYearmonth(), DateTimeFormatter.ofPattern("yyyy-MM"));
    }

}
