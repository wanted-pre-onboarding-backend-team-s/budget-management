package com.wanted.bobo.budget.domain;

import com.wanted.bobo.budget.dto.BudgetRequest;
import com.wanted.bobo.budget.exception.NotMatchUserException;
import com.wanted.bobo.category.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Enumerated(EnumType.STRING)
    private Category category;

    @Builder
    public Budget(int amount, Long userId, Category category) {
        this.amount = amount;
        this.userId = userId;
        this.category = category;
    }

    public void verifyMatchUser(Long userId) {
        if(!this.userId.equals(userId)) {
            throw new NotMatchUserException();
        }
    }
    public boolean verifyEqualCategory(String category) {
        return this.category.equals(Category.of(category));
    }

    public void changeInfo(BudgetRequest request) {
        this.amount = request.getAmount();
        this.category = Category.of(request.getCategory());
    }

}
