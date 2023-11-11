package com.saving.category.controller;

import com.saving.category.dto.CategoryListResponseDto;
import com.saving.category.service.CategoryService;
import com.saving.common.response.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ApiResponse<List<CategoryListResponseDto>> categoryList(@RequestAttribute Long userId) {
        return ApiResponse.ok(categoryService.getCategoryList(userId));
    }
}
