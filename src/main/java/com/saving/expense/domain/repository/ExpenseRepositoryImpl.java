package com.saving.expense.domain.repository;

import static com.saving.category.domain.entity.QCategory.category;
import static com.saving.expense.domain.entity.QExpense.expense;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.saving.expense.dto.SimpleExpenseDto;
import com.saving.expense.dto.TotalExpenseByCategory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExpenseRepositoryImpl implements ExpenseRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<SimpleExpenseDto> listOfTimeBasedExpense(Long userId, String startDate,
            String endDate, Long categoryId, Boolean minAmount, Boolean maxAmount) {

        return jpaQueryFactory
                .select(Projections.constructor(
                        SimpleExpenseDto.class,
                        expense.categoryId,
                        category.categoryName,
                        expense.amount,
                        expense.expenseAt))
                .from(expense)
                .leftJoin(category)
                .on(expense.categoryId.eq(category.id))
                .where(category.userId.eq(userId)
                        .and(expense.expenseAt.between(startDate, endDate)
                        .and(expense.isTotalExpenseApply.eq(1))))
                .orderBy(expense.expenseAt.desc())
                .fetch();
    }

    @Override
    public Long totalExpense(Long userId, String startDate,
            String endDate, Long categoryId, Boolean minAmount, Boolean maxAmount) {

        return jpaQueryFactory
                .select(expense.amount.sum())
                .from(expense)
                .leftJoin(category)
                .on(expense.categoryId.eq(category.id))
                .where(category.userId.eq(userId)
                        .and(expense.expenseAt.between(startDate, endDate)
                        .and(expense.isTotalExpenseApply.eq(1))))
                .fetchOne().longValue();
    }

    @Override
    public List<TotalExpenseByCategory> listOfCategoryBasedExpense(Long userId, String startDate,
            String endDate, Long categoryId, Boolean minAmount, Boolean maxAmount) {

        return jpaQueryFactory
                .select(Projections.constructor(
                        TotalExpenseByCategory.class,
                        category.id.as("categoryId"),
                        category.categoryName,
                        expense.amount.sum().as("categoryTotalExpense")))
                .from(expense)
                .leftJoin(category)
                .on(expense.categoryId.eq(category.id))
                .where(category.userId.eq(userId)
                        .and(expense.expenseAt.between(startDate, endDate)
                        .and(expense.isTotalExpenseApply.eq(1))))
                .groupBy(expense.categoryId)
                .orderBy(expense.amount.sum().desc())
                .fetch();
    }
}
