package com.saving.budget.domain;

import com.saving.common.domain.entity.BaseCreateTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "budgets")
@Entity
@Getter
@NoArgsConstructor
public class Budget extends BaseCreateTimeEntity {

    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private Long categoryId;
    private int amount;
    private String budgetYearMonth;

    @Builder
    public Budget(Long userId, Long categoryId, int amount, String budgetYearMonth) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.budgetYearMonth = budgetYearMonth + "-01";
    }
}
