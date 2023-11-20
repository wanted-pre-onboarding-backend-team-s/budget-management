package com.saving.category.controller;

import com.saving.category.dto.CategoryListResponseDto;
import com.saving.category.service.CategoryService;
import com.saving.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "카테고리", description = "카테고리 관련 API")
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리 목록 조회", description = "해당 사용자의 카테고리 목록을 조회합니다.")
    @GetMapping
    public ApiResponse<List<CategoryListResponseDto>> categoryList(@RequestAttribute Long userId) {
        return ApiResponse.ok(categoryService.getCategoryList(userId));
    }
}
