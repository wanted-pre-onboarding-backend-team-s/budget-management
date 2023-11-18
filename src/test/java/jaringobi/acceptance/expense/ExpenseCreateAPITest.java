package jaringobi.acceptance.expense;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.path.json.JsonPath;
import jaringobi.acceptance.APITest;
import jaringobi.domain.expense.ExpenseRepository;
import jaringobi.exception.ErrorType;
import jaringobi.exception.auth.AuthenticationErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("지출 API 테스트")
public class ExpenseCreateAPITest extends APITest {

    @Autowired
    ExpenseRepository expenseRepository;

    @Nested
    @DisplayName("[지출 등록] /api/v1/expenditures ")
    class CreateApiTest {

        @Test
        @DisplayName("성공 200")
        void success() {
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
            var response = ExpenseAPI.지출추가요청(body, accessToken);
            JsonPath jsonPath = response.jsonPath();

            // Then
            assertThat(response.response().statusCode()).isEqualTo(200);
            assertThat(jsonPath.getString("code")).isEqualTo("200");
            assertThat(jsonPath.getString("data.expenseNo")).isNotEmpty();
        }

        @Test
        @DisplayName("성공 200 - 합계여부를 보내지 않는 경우")
        void successNullExcludeTotalExpense() {
            var body = """
                    {
                        "memo": "친구들이랑 점심 짬뽕",
                        "categoryId": 3,
                        "expenseMount": 10000,
                        "expenseDateTime": "2023-10-02T02:11:00"
                    }
                    """;

            // When
            var response = ExpenseAPI.지출추가요청(body, accessToken);
            JsonPath jsonPath = response.jsonPath();

            // Then
            assertThat(response.response().statusCode()).isEqualTo(200);
            assertThat(jsonPath.getString("code")).isEqualTo("200");
            assertThat(jsonPath.getString("data.expenseNo")).isNotEmpty();
        }

        @Test
        @DisplayName("실패 400 - 지출 일이 없을 경우")
        void failExpenseDateTimeIsNull() {
            var body = """
                    {
                        "memo": "친구들이랑 점심 짬뽕",
                        "categoryId": 3,
                        "expenseMount": 10000
                    }
                    """;

            // When
            var response = ExpenseAPI.지출추가요청(body, accessToken);
            JsonPath jsonPath = response.jsonPath();

            // Then
            assertThat(response.response().statusCode()).isEqualTo(400);
            assertThat(jsonPath.getString("code")).isEqualTo(ErrorType.E001.getCode());
            assertThat(jsonPath.getString("errorFields[0].field")).isEqualTo("expenseDateTime");
            assertThat(jsonPath.getString("errorFields[0].message")).contains("지출일은 필수입니다.");
        }

        @Test
        @DisplayName("실패 400 - 카테고리를 입력하지 않을경우")
        void failCategoryIsNull() {
            var body = """
                    {
                        "memo": "친구들이랑 점심 짬뽕",
                        "expenseMount": 10000,
                        "expenseDateTime": "2023-10-02T02:11:00"
                    }
                    """;

            // When
            var response = ExpenseAPI.지출추가요청(body, accessToken);
            JsonPath jsonPath = response.jsonPath();

            // Then
            assertThat(response.response().statusCode()).isEqualTo(400);
            assertThat(jsonPath.getString("code")).isEqualTo(ErrorType.E001.getCode());
            assertThat(jsonPath.getString("errorFields[0].field")).isEqualTo("categoryId");
            assertThat(jsonPath.getString("errorFields[0].message")).contains("카테고리는 필수입니다.");
        }

        @ParameterizedTest
        @DisplayName("실패 400 - 카테고리가 음수 또는 0인 경우")
        @ValueSource(ints = {-1, 0})
        void failCategoryIsNegativeOrZero(int categoryId) {
            var body = String.format("""
                    {
                        "memo": "친구들이랑 점심 짬뽕",
                        "expenseMount": 10000,
                        "categoryId": %d,
                        "expenseDateTime": "2023-10-02T02:11:00"
                    }
                    """, categoryId);

            // When
            var response = ExpenseAPI.지출추가요청(body, accessToken);
            JsonPath jsonPath = response.jsonPath();

            // Then
            assertThat(response.response().statusCode()).isEqualTo(400);
            assertThat(jsonPath.getString("code")).isEqualTo(ErrorType.E001.getCode());
            assertThat(jsonPath.getString("errorFields[0].field")).isEqualTo("categoryId");
            assertThat(jsonPath.getString("errorFields[0].message")).contains("잘못된 카테고리 번호입니다.");
        }


        @Test
        @DisplayName("실패 400 - 지출금액이 없는 경우")
        void failExpenseMountIsNull() {
            var body = """
                    {
                        "memo": "친구들이랑 점심 짬뽕",
                        "categoryId": 1,
                        "expenseDateTime": "2023-10-02T02:11:00"
                    }
                    """;

            // When
            var response = ExpenseAPI.지출추가요청(body, accessToken);
            JsonPath jsonPath = response.jsonPath();

            // Then
            assertThat(response.response().statusCode()).isEqualTo(400);
            assertThat(jsonPath.getString("code")).isEqualTo(ErrorType.E001.getCode());
            assertThat(jsonPath.getString("errorFields[0].field")).isEqualTo("expenseMount");
            assertThat(jsonPath.getString("errorFields[0].message")).contains("지출 금액은 필수입니다.");
        }

        @ParameterizedTest
        @DisplayName("실패 400 - 지출금액이 음수 또는 0인 경우")
        @ValueSource(ints = {-1, 0})
        void failExpenseMountIsNegativeOrZero(int amount) {
            var body = String.format("""
                    {
                        "memo": "친구들이랑 점심 짬뽕",
                        "expenseMount": %d,
                        "categoryId": 1,
                        "expenseDateTime": "2023-10-02T02:11:00"
                    }
                    """, amount);

            // When
            var response = ExpenseAPI.지출추가요청(body, accessToken);
            JsonPath jsonPath = response.jsonPath();

            // Then
            assertThat(response.response().statusCode()).isEqualTo(400);
            assertThat(jsonPath.getString("code")).isEqualTo(ErrorType.E001.getCode());
            assertThat(jsonPath.getString("errorFields[0].field")).isEqualTo("expenseMount");
            assertThat(jsonPath.getString("errorFields[0].message")).contains("지출 금액은 최소 0원 보다 커야합니다.");
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
            var response = ExpenseAPI.지출추가요청(body, null);
            JsonPath jsonPath = response.jsonPath();

            // Then
            assertThat(response.response().statusCode()).isEqualTo(403);
            assertThat(jsonPath.getString("code")).isEqualTo(AuthenticationErrorType.INVALID_TOKEN.getCode());
            assertThat(jsonPath.getString("message")).isEqualTo(AuthenticationErrorType.INVALID_TOKEN.getMessage());
        }
    }
}
