package com.saving.category.budget.dto;

import lombok.Getter;

@Getter
public class ResultTotalBudgetByCategoryNameDto {

    private String categoryName;
    private Long totalBudgetByCategory;
    private Double averageBudget;

    public ResultTotalBudgetByCategoryNameDto(String categoryName, Long totalBudgetByCategory) {
        this.categoryName = categoryName;
        this.totalBudgetByCategory = totalBudgetByCategory;
    }

    public void setAverageBudget(Long totalBudget) {
        this.averageBudget = (double) totalBudgetByCategory / totalBudget;
    }
}