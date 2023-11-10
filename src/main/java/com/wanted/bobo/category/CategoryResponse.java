package com.wanted.bobo.category;


import lombok.Getter;

@Getter
public class CategoryResponse {

    private final String code;
    private final String name;

    public CategoryResponse(Category category) {
        this.name = category.getName();
        this.code = category.getCode();
    }

}
