package com.saving.user.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1137193208L;

    public static final QUser user = new QUser("user");

    public final com.saving.common.domain.entity.QBaseTimeEntity _super = new com.saving.common.domain.entity.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath encodedPassword = createString("encodedPassword");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> isTodayBudgetNotice = createNumber("isTodayBudgetNotice", Integer.class);

    public final NumberPath<Integer> isTodayExpenseNotice = createNumber("isTodayExpenseNotice", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final StringPath username = createString("username");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

