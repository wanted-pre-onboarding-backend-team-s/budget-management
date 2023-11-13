package com.wanted.bobo.budget.dto;

import com.wanted.bobo.category.Category;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BudgetRecommendationResponse {

    private Map<Category, Integer> recommendation;
}
