package com.saving.category.budget.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBudget is a Querydsl query type for Budget
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBudget extends EntityPathBase<Budget> {

    private static final long serialVersionUID = -1725118522L;

    public static final QBudget budget = new QBudget("budget");

    public final com.saving.common.domain.entity.QBaseCreateTimeEntity _super = new com.saving.common.domain.entity.QBaseCreateTimeEntity(this);

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    public final StringPath budgetYearMonth = createString("budgetYearMonth");

    public final NumberPath<Long> categoryId = createNumber("categoryId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QBudget(String variable) {
        super(Budget.class, forVariable(variable));
    }

    public QBudget(Path<? extends Budget> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBudget(PathMetadata metadata) {
        super(Budget.class, metadata);
    }

}

