package com.saving.category.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "default_categories")
@Entity
@Getter
@NoArgsConstructor
public class DefaultCategory {

    @Id
    @GeneratedValue
    private Integer id;
    private String defaultCategoryName;
    private double defaultBudget;

    @Builder
    public DefaultCategory(int id, String defaultCategoryName, double defaultBudget) {
        this.id = id;
        this.defaultCategoryName = defaultCategoryName;
        this.defaultBudget = defaultBudget;
    }
}
