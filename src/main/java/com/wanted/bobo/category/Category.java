package com.wanted.bobo.category;

import com.wanted.bobo.category.exception.NotAvailableCategoryException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public enum Category {
    ETC("etc", "기타"),
    FOOD("food", "식ㅤ비"),
    TRAFFIC("traffic", "교통비"),
    COMMUNICATION("communication", "통신비"),
    LIVING("living", "생활비"),
    HOUSING("housing", "주거비"),
    HEALTHCARE("healthcare", "의료비"),
    ENTERTAINMENT("entertainment", "문화비"),
    CLOTHING("clothing", "의류비");

    private final String value;
    private final String name;

    private static final Map<String, Category> CATEGORY_MAP = new HashMap<>();

    static {
        for (Category category : values()) {
            CATEGORY_MAP.put(category.value, category);
        }
    }

    Category(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public static List<Category> toList() {
        return CATEGORY_MAP.values()
                           .stream()
                           .filter(category -> category != ETC)
                           .toList();
    }

    public static Category of(String value) {
        if(!CATEGORY_MAP.containsKey(value) || value.equals(ETC.value)) {
            throw new NotAvailableCategoryException();
        }

        return CATEGORY_MAP.get(value);
    }
}
