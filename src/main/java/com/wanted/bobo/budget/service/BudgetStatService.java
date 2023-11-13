package com.wanted.bobo.budget.service;

import com.wanted.bobo.budget.domain.BudgetRepository;
import com.wanted.bobo.budget.dto.BudgetRecommendationResponse;
import com.wanted.bobo.budget.dto.BudgetStatByCategory;
import com.wanted.bobo.category.Category;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BudgetStatService {

    private final BudgetRepository budgetRepository;
    private Map<Category, Integer> recommendation;

    private static final int PERCENTAGE_THRESHOLD = 10;
    private static final double PERCENTAGE_MULTIPLIER = 0.01;
    private static final int ROUNDING_THRESHOLD = 50;
    private static final int ROUNDING_DIGITS = 2;

    @Transactional(readOnly = true)
    public BudgetRecommendationResponse recommendBudget(int amount) {
        initializeRecommendations();

        List<BudgetStatByCategory> stats = budgetRepository.findAverageBudgetPercentagesByCategory();

        for (BudgetStatByCategory stat : stats) {
            updateCategoryBudgetRecommendation(stat.getPercentage(), stat.getCategory(), amount);
        }

        updateEtcCategoryBudgetRecommendation(amount);

        return BudgetRecommendationResponse.builder()
                                           .recommendation(recommendation)
                                           .build();
    }

    private void initializeRecommendations() {
        recommendation = new EnumMap<>(Category.class);
        recommendation.put(Category.ETC, 0);
    }

    private void updateEtcCategoryBudgetRecommendation(int amount) {
        recommendation.put(
                Category.ETC,
                updateAmount(Math.round(recommendation.get(Category.ETC) * PERCENTAGE_MULTIPLIER * amount))
        );
    }

    private void updateCategoryBudgetRecommendation(int percentage, Category category, int amount) {
        if (percentage > PERCENTAGE_THRESHOLD) {
            recommendation.put(
                    category, updateAmount(Math.round(PERCENTAGE_MULTIPLIER * percentage * amount))
            );
        } else {
            recommendation.put(Category.ETC, recommendation.get(Category.ETC) + percentage);
        }
    }

    private int updateAmount(long amount) {
        int roundedAmount = (int) ((amount + ROUNDING_THRESHOLD) / 100) * 100;
        return roundedAmount - (roundedAmount % (int) Math.pow(10, ROUNDING_DIGITS));
    }
}
