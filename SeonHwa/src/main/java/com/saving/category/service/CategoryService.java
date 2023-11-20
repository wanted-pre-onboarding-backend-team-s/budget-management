package com.saving.category.service;

import com.saving.category.domain.entity.Category;
import com.saving.category.domain.entity.DefaultCategory;
import com.saving.category.domain.repository.CategoryRepository;
import com.saving.category.domain.repository.DefaultCategoryRepository;
import com.saving.category.dto.CategoryListResponseDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final DefaultCategoryRepository defaultCategoryRepository;

    @Transactional
    public void createDefaultCategories(Long userId) {

        List<DefaultCategory> getDefaultCategoryList = defaultCategoryRepository.findAll();
        for (DefaultCategory defaultCategory : getDefaultCategoryList) {
            categoryRepository.save(Category.builder()
                    .userId(userId)
                    .categoryName(defaultCategory.getDefaultCategoryName())
                    .build());
        }
    }

    @Transactional(readOnly = true)
    public List<CategoryListResponseDto> getCategoryList(Long userId) {

        List<CategoryListResponseDto> list = new ArrayList<>();
        List<Category> allByUserId = categoryRepository.findAllByUserId(userId);
        for (Category category : allByUserId) {
            list.add(new CategoryListResponseDto(category));
        }

        return list;
    }
}
