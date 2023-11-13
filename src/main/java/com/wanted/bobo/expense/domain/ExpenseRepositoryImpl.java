package com.wanted.bobo.expense.domain;

import static com.wanted.bobo.expense.domain.QExpense.expense;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.bobo.category.Category;
import com.wanted.bobo.expense.dto.ExpenseFilter;
import com.wanted.bobo.expense.dto.ExpenseResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExpenseRepositoryImpl implements ExpenseRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ExpenseResponse> findByCondition(Long userId, ExpenseFilter filter) {
        List<ExpenseResponse> filteredExpenses = jpaQueryFactory
                .select(Projections.constructor(
                        ExpenseResponse.class,
                        expense.id,
                        expense.amount,
                        expense.memo,
                        expense.date,
                        expense.category,
                        expense.isExclude))
                .from(expense)
                .where(userEq(userId),
                       dateBetween(filter.getStart(), filter.getEnd()),
                       categoryEq(filter.getCategory()),
                       amountBetween(filter.getMin(), filter.getMax()))
                .fetch();

        return filteredExpenses;
    }

    private BooleanExpression userEq(Long userId) {
        return expense.userId.eq(userId);
    }

    private BooleanExpression dateBetween(String start, String end) {
        return expense.date.between(parseDate(start), parseDate(end));
    }

    private LocalDate parseDate(String date) {
        return date != null ? LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
    }

    private BooleanExpression categoryEq(String category) {
        if (StringUtils.isNullOrEmpty(category)) {
            return Expressions.TRUE;
        }

        return expense.category.eq(Category.of(category));
    }

    private BooleanExpression amountBetween(Integer min, Integer max) {
        if (min != null) {
            return max != null ? expense.amount.between(min, max) : expense.amount.goe(min);
        } else if (max != null) {
            return expense.amount.loe(max);
        } else {
            return expense.amount.isNotNull();
        }
    }
}