package jaringobi.acceptance.category;

import static org.assertj.core.api.Assertions.*;

import jaringobi.acceptance.APITest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("카테고리 API 테스트")
public class CategoryAPITest extends APITest {

    @Test
    @DisplayName("성공 : [카테고리 목록조회] /api/v1/categories")
    void test() {
        var response = CategoryAPI.카테고리목록요청();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

}
