package com.saving.expense.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QExpense is a Querydsl query type for Expense
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExpense extends EntityPathBase<Expense> {

    private static final long serialVersionUID = -2079668518L;

    public static final QExpense expense = new QExpense("expense");

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    public final NumberPath<Long> categoryId = createNumber("categoryId", Long.class);

    public final StringPath content = createString("content");

    public final StringPath expenseAt = createString("expenseAt");

    public final EnumPath<com.saving.expense.domain.enums.ExpenseMethod> expenseMethod = createEnum("expenseMethod", com.saving.expense.domain.enums.ExpenseMethod.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> isTotalExpenseApply = createNumber("isTotalExpenseApply", Integer.class);

    public QExpense(String variable) {
        super(Expense.class, forVariable(variable));
    }

    public QExpense(Path<? extends Expense> path) {
        super(path.getType(), path.getMetadata());
    }

    public QExpense(PathMetadata metadata) {
        super(Expense.class, metadata);
    }

}

