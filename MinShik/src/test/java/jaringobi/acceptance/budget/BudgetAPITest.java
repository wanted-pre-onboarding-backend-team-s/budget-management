package jaringobi.acceptance.budget;


import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.path.json.JsonPath;
import jaringobi.acceptance.APITest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("예산 API 테스트")
public class BudgetAPITest extends APITest {

    @Nested
    @DisplayName("[예산 설정] /api/v1/budget ")
    class BudgetCreate {

        @Test
        @DisplayName("성공 200")
        void successCreateBudget() {
            // Given
            String body = """
                    {
                        "budgetByCategories" : [
                            {
                                "categoryId": 1,
                                "money": 1
                            },
                            {
                                "categoryId": 2,
                                "money": 9000
                            }
                        ],
                        "month": "2023-10"
                    }
                    """;
            // When
            var response = BudgetAPI.예산설정(body, accessToken);

            // Then
            assertThat(response.response().statusCode()).isEqualTo(201);
            assertThat(response.header("Location")).isEqualTo("/api/v1/budget/1");
        }

        @Test
        @DisplayName("실패 400 - 중복된 카테고리 값")
        void failDuplicatedCategory() {
            String body = """
                    {
                        "budgetByCategories" : [
                            {
                                "categoryId": 1,
                                "money": 1
                            },
                            {
                                "categoryId": 1,
                                "money": 9000
                            }
                        ],
                        "month": "2023-10"
                    }
                    """;
            // When
            var response = BudgetAPI.예산설정(body, accessToken);
            JsonPath jsonPath = response.jsonPath();

            // Then
            assertThat(response.response().statusCode()).isEqualTo(400);
            assertThat(jsonPath.getString("code")).isEqualTo("E001");
            assertThat(jsonPath.getString("message")).isEqualTo("필드 값 에러");
            assertThat(jsonPath.getString("errorFields[0].message")).isEqualTo("중복 없이 적어도 하나의 카테고리별 예산이 포함되어야 합니다.");
        }

        @Test
        @DisplayName("실패 400 - 비어있는 카테고리 예산")
        void failCategoryIsNull() {
            // Given
            String body = """
                    {
                        "month": "2023-10"
                    }
                    """;
            // When
            var response = BudgetAPI.예산설정(body, accessToken);
            JsonPath jsonPath = response.jsonPath();

            // Then
            assertThat(response.response().statusCode()).isEqualTo(400);
            assertThat(jsonPath.getString("code")).isEqualTo("E001");
            assertThat(jsonPath.getString("message")).isEqualTo("필드 값 에러");
            assertThat(jsonPath.getString("errorFields[0].message")).isEqualTo("중복 없이 적어도 하나의 카테고리별 예산이 포함되어야 합니다.");
        }

        @Test
        @DisplayName("실패 400 - 잘못된 날짜 포맷형식")
        void failInvalidDateFormat() {
            // Given
            String body = """
                    {
                        "budgetByCategories" : [
                            {
                                "categoryId": 1,
                                "money": 1
                            },
                            {
                                "categoryId": 2,
                                "money": 9000
                            }
                        ],
                        "month": "2023"
                    }
                    """;
            // When
            var response = BudgetAPI.예산설정(body, accessToken);
            JsonPath jsonPath = response.jsonPath();

            // Then
            assertThat(response.response().statusCode()).isEqualTo(400);
            assertThat(jsonPath.getString("code")).isEqualTo("E001");
            assertThat(jsonPath.getString("message")).isEqualTo("필드 값 에러");
            assertThat(jsonPath.getString("errorFields[0].message")).isEqualTo("예산 날짜는 'yyyy-MM' 형식이어야 합니다.");
        }


    }
}
