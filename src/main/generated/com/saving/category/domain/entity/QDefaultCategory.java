package com.saving.category.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDefaultCategory is a Querydsl query type for DefaultCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDefaultCategory extends EntityPathBase<DefaultCategory> {

    private static final long serialVersionUID = 1085654389L;

    public static final QDefaultCategory defaultCategory = new QDefaultCategory("defaultCategory");

    public final StringPath defaultCategoryName = createString("defaultCategoryName");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public QDefaultCategory(String variable) {
        super(DefaultCategory.class, forVariable(variable));
    }

    public QDefaultCategory(Path<? extends DefaultCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDefaultCategory(PathMetadata metadata) {
        super(DefaultCategory.class, metadata);
    }

}

