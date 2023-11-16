package com.saving.expense.domain.repository;

import static com.saving.category.domain.entity.QCategory.category;
import static com.saving.expense.domain.entity.QExpense.expense;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.saving.expense.dto.ExpenseResponseDto;
import com.saving.expense.dto.SimpleExpenseDto;
import com.saving.expense.dto.TotalExpenseByCategory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExpenseRepositoryImpl implements ExpenseRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<SimpleExpenseDto> listOfTimeBasedExpense(Long userId, String startDate,
            String endDate, Long categoryId, Integer minAmount, Integer maxAmount) {

        return jpaQueryFactory
                .select(Projections.constructor(
                        SimpleExpenseDto.class,
                        expense.categoryId,
                        category.categoryName,
                        expense.amount,
                        expense.expenseAt))
                .from(expense)
                .join(category)
                .on(expense.categoryId.eq(category.id))
                .where(category.userId.eq(userId)
                        .and(expense.expenseAt.between(startDate, endDate))
                        .and(expense.isTotalExpenseApply.eq(1))
                        .and(categoryIdEq(categoryId))
                        .and(amountRangeEq(minAmount, maxAmount)))
                .orderBy(expense.expenseAt.desc())
                .fetch();
    }

    @Override
    public Optional<Long> totalExpense(Long userId, String startDate,
            String endDate, Long categoryId, Integer minAmount, Integer maxAmount) {

        return Optional.of(
                jpaQueryFactory
                .select(expense.amount.sum().coalesce(0))
                .from(expense)
                .join(category)
                .on(expense.categoryId.eq(category.id))
                .where(category.userId.eq(userId)
                        .and(expense.expenseAt.between(startDate, endDate))
                        .and(expense.isTotalExpenseApply.eq(1))
                        .and(categoryIdEq(categoryId))
                        .and(amountRangeEq(minAmount, maxAmount)))
                .fetchOne().longValue());
    }

    @Override
    public List<TotalExpenseByCategory> listOfCategoryBasedExpense(Long userId, String startDate,
            String endDate, Long categoryId, Integer minAmount, Integer maxAmount) {

        return jpaQueryFactory
                .select(Projections.constructor(
                        TotalExpenseByCategory.class,
                        category.id.as("categoryId"),
                        category.categoryName,
                        expense.amount.sum().as("categoryTotalExpense")))
                .from(expense)
                .join(category)
                .on(expense.categoryId.eq(category.id))
                .where(category.userId.eq(userId)
                        .and(expense.expenseAt.between(startDate, endDate))
                        .and(expense.isTotalExpenseApply.eq(1))
                        .and(categoryIdEq(categoryId))
                        .and(amountRangeEq(minAmount, maxAmount)))
                .groupBy(expense.categoryId)
                .orderBy(expense.amount.sum().desc())
                .fetch();
    }

    private BooleanExpression categoryIdEq(Long categoryId) {
        return categoryId != null ? expense.categoryId.eq(categoryId) : null;
    }

    private BooleanExpression amountRangeEq(Integer minAmount, Integer maxAmount) {
        return minAmount != null ? expense.amount.between(minAmount, maxAmount) : null;
    }

    @Override
    public Optional<ExpenseResponseDto> findByIdAndUserId(Long expenseId, Long userId) {

        return Optional.ofNullable(
                jpaQueryFactory
                .select(Projections.constructor(
                        ExpenseResponseDto.class,
                        expense.id.as("expenseId"),
                        expense.categoryId,
                        expense.expenseMethod,
                        expense.amount,
                        expense.content,
                        expense.isTotalExpenseApply,
                        expense.expenseAt))
                .from(expense)
                .leftJoin(category)
                .on(expense.categoryId.eq(category.id))
                .where(category.userId.eq(userId).and(expense.id.eq(expenseId)))
                .fetchOne());
    }

    @Override
    public boolean existsByIdAndUserId(Long expenseId, Long userId) {

        Integer fetchOne = jpaQueryFactory
                .selectOne()
                .from(expense)
                .leftJoin(category)
                .on(expense.categoryId.eq(category.id))
                .where(category.userId.eq(userId).and(expense.id.eq(expenseId)))
                .fetchFirst();
        return fetchOne != null;
    }
}
