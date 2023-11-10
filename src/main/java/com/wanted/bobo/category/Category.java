package com.wanted.bobo.category;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public enum Category {
    FOOD("C001", "식비"),
    TRAFFIC("C002", "교통비"),
    COMMUNICATION("C003", "통신비"),
    LIVING("C004", "생활비"),
    HOUSING("C005", "주거비"),
    HEALTHCARE("C006", "의료비"),
    ENTERTAINMENT("C007", "문화비"),
    APPAREL("C008", "의류비");

    private final String code;
    private final String value;

    private static final Map<String, Category> CATEGORY_MAP = new HashMap<>();

    static {
        for (Category category : values()) {
            CATEGORY_MAP.put(category.code, category);
        }
    }

    Category(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static List<CategoryResponse> toList() {
        return CATEGORY_MAP.values()
                           .stream()
                           .map(CategoryResponse::new)
                           .toList();
    }

}
