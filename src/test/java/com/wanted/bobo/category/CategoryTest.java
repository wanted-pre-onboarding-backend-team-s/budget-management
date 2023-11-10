package com.wanted.bobo.category;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

class CategoryTest {

    @DisplayName("카테고리 목록 조회 성공")
    @Test
    void category_list_success() {
        List<CategoryResponse> result = Category.toList();
        assertThat(result.size()).isEqualTo(8);
    }

    @DisplayName("카테고리 목록 조회 Stream, HashMap 차이")
    @Test
    void category_list_stream_and_hashmap() {
        StopWatch stopWatch = new StopWatch("category");

        stopWatch.start("byStream");
        List<CategoryResponse> cr = Arrays.stream(Category.values())
                                          .map(CategoryResponse::new)
                                          .toList();
        stopWatch.stop();

        stopWatch.start("byMap");
        Category.toList();
        stopWatch.stop();

        System.out.println(stopWatch.shortSummary());
        System.out.println(stopWatch.getTotalTimeMillis());
        System.out.println(stopWatch.prettyPrint());

    }
}