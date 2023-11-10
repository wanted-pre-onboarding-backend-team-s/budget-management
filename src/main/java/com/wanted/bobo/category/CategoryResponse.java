package com.wanted.bobo.category;


import lombok.Getter;

@Getter
public class CategoryResponse {

    private final String name;
    private final String code;

    public CategoryResponse(Category category) {
        this.name = category.getValue();
        this.code = category.getCode();
    }

}
