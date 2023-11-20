package com.wanted.bobo.budget.dto;

import com.wanted.bobo.category.Category;

public interface BudgetStatByCategory {
    Category getCategory();
    int getPercentage();
}
