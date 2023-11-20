package com.saving.category.domain.entity;

import com.saving.common.domain.entity.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "categories")
@Entity
@Getter
@NoArgsConstructor
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String categoryName;

    @Builder
    public Category(Long userId, String categoryName) {
        this.userId = userId;
        this.categoryName = categoryName;
    }
}
