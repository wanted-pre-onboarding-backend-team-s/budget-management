package jaringobi.acceptance.expense;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.path.json.JsonPath;
import jaringobi.acceptance.APITest;
import jaringobi.domain.expense.ExpenseRepository;
import jaringobi.exception.ErrorType;
import jaringobi.exception.auth.AuthenticationErrorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("지출 삭제 API 테스트 : /api/v1/expenditures/{id}")
public class ExpenseDeleteAPITest extends APITest {

    @Autowired
    ExpenseRepository expenseRepository;

    @Nested
    @DisplayName("[지출 삭제] /api/v1/expenditures/{id} ")
    class DeleteApiTest {

        @BeforeEach
        void setUp() {
            var body = """
                    {
                        "memo": "친구들이랑 점심 짬뽕",
                        "categoryId": 1,
                        "expenseMount": 10000,
                        "expenseDateTime": "2023-10-02T02:11:00",
                        "excludeTotalExpense": true
                    }
                    """;
            ExpenseAPI.지출추가요청(body, accessToken);
        }

        @Test
        @DisplayName("성공 200")
        void success() {
            // When
            var response = ExpenseAPI.지출삭제요청(1L, accessToken);

            // Then
            assertThat(response.response().statusCode()).isEqualTo(200);
            assertThat(expenseRepository.existsById(1L)).isFalse();
        }

        @Test
        @DisplayName("실패 404 - 존재하지 않는 지출 정보")
        void failNoExistedExpense() {
            // When
            var response = ExpenseAPI.지출삭제요청(2L, accessToken);
            JsonPath jsonPath = response.jsonPath();

            // Then
            assertThat(response.response().statusCode()).isEqualTo(404);
            assertThat(jsonPath.getString("code")).isEqualTo(ErrorType.E003.getCode());
            assertThat(jsonPath.getString("message")).isEqualTo(ErrorType.E003.getMessage());
            assertThat(expenseRepository.existsById(1L)).isTrue();
        }

        @Test
        @DisplayName("실패 403 - 잘못된 권한")
        void failNoPermission() {
            // When
            var response = ExpenseAPI.지출삭제요청(1L, anotherUserAccessToken);
            JsonPath jsonPath = response.jsonPath();

            // Then
            assertThat(response.response().statusCode()).isEqualTo(403);
            assertThat(jsonPath.getString("code")).isEqualTo(AuthenticationErrorType.NO_PERMISSION.getCode());
            assertThat(jsonPath.getString("message")).isEqualTo(AuthenticationErrorType.NO_PERMISSION.getMessage());
            assertThat(expenseRepository.existsById(1L)).isTrue();
        }

        @Test
        @DisplayName("실패 403 - 토큰 없이 접근")
        void failNonAuthenticatedUser() {
            var body = """
                    {
                        "memo": "친구들이랑 점심 짬뽕",
                        "categoryId": 3,
                        "expenseMount": 10000,
                        "expenseDateTime": "2023-10-02T02:11:00",
                        "excludeTotalExpense": true
                    }
                    """;

            // When
            var response = ExpenseAPI.지출삭제요청(1L, null);
            JsonPath jsonPath = response.jsonPath();

            // Then
            assertThat(response.response().statusCode()).isEqualTo(403);
            assertThat(jsonPath.getString("code")).isEqualTo(AuthenticationErrorType.INVALID_TOKEN.getCode());
            assertThat(jsonPath.getString("message")).isEqualTo(AuthenticationErrorType.INVALID_TOKEN.getMessage());
        }
    }
}
