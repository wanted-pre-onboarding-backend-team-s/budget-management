package jaringobi.acceptance.expense;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.path.json.JsonPath;
import jaringobi.acceptance.APITest;
import jaringobi.domain.expense.Expense;
import jaringobi.domain.expense.ExpenseRepository;
import jaringobi.domain.user.UserRepository;
import jaringobi.exception.ErrorType;
import jaringobi.exception.auth.AuthenticationErrorType;
import jaringobi.exception.expense.ExpenseNotFoundException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("[지출 수정] /api/v1/expenditures/{id}")
public class ExpenseModifyAPITest extends APITest {

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    UserRepository userRepository;

    @Nested
    @DisplayName("[지출 수정] /api/v1/expenditures/{id} ")
    class ModifyApiTest {

        @BeforeEach
        void setUp() {
            // Given
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
        @DisplayName("성공 204 - 메모도 같이 수정")
        void success() {
            var modifyBody = """
                    {
                        "memo": "친구들이랑 점심 탕수육",
                        "categoryId": 2,
                        "expenseMount": 15000,
                        "expenseDateTime": "2023-01-01T00:00:00",
                        "excludeTotalExpense": false
                    }
                    """;
            // When
            var response = ExpenseAPI.지출수정요청(1L, modifyBody, accessToken);
            Expense expense = findExpense();

            // Then
            assertThat(response.response().statusCode()).isEqualTo(204);
            assertThat(expense.getCategory().getId()).isEqualTo(2L);
            assertThat(expense.getExpenseAt()).isEqualTo(LocalDateTime.of(2023, 01, 01, 0, 0, 0));
            assertThat(expense.getMemo()).isEqualTo("친구들이랑 점심 탕수육");
            assertThat(expense.isExcludeInTotal()).isFalse();
        }

        @Test
        @DisplayName("성공 204 - 메모는 수정 하지 않음")
        void successNoMemo() {
            var modifyBody = """
                    {
                        "categoryId": 2,
                        "expenseMount": 15000,
                        "expenseDateTime": "2023-01-01T00:00:00",
                        "excludeTotalExpense": false
                    }
                    """;
            // When
            var response = ExpenseAPI.지출수정요청(1L, modifyBody, accessToken);
            Expense expense = findExpense();

            // Then
            assertThat(response.response().statusCode()).isEqualTo(204);
            assertThat(expense.getCategory().getId()).isEqualTo(2L);
            assertThat(expense.getExpenseAt()).isEqualTo(LocalDateTime.of(2023, 01, 01, 0, 0, 0));
            assertThat(expense.getMemo()).isEqualTo("친구들이랑 점심 짬뽕");
            assertThat(expense.isExcludeInTotal()).isFalse();
        }

        @Test
        @DisplayName("실패 403 - 다른 유저가 지출 수정")
        void failNoPermission() {
            var modifyBody = """
                    {
                        "memo": "친구들이랑 점심 탕수육",
                        "categoryId": 2,
                        "expenseMount": 15000,
                        "expenseDateTime": "2023-01-01T00:00:00",
                        "excludeTotalExpense": false
                    }
                    """;
            // When
            var response = ExpenseAPI.지출수정요청(1L, modifyBody, anotherUserAccessToken);

            // Then
            assertThat(response.response().statusCode()).isEqualTo(403);
        }

        @Test
        @DisplayName("실패 404 - 존재하지 않는 지출 수정 요청")
        void failNoExistExpense() {
            var modifyBody = """
                    {
                        "memo": "친구들이랑 점심 탕수육",
                        "categoryId": 382,
                        "expenseMount": 15000,
                        "expenseDateTime": "2023-01-01T00:00:00",
                        "excludeTotalExpense": false
                    }
                    """;
            // When
            var response = ExpenseAPI.지출수정요청(1L, modifyBody, accessToken);

            // Then
            assertThat(response.response().statusCode()).isEqualTo(400);
        }

        @Test
        @DisplayName("실패 400 - 존재하지 않는 카테고리로 수정 요청")
        void failNoExistCategory() {
            var modifyBody = """
                    {
                        "memo": "친구들이랑 점심 탕수육",
                        "categoryId": 382,
                        "expenseMount": 15000,
                        "expenseDateTime": "2023-01-01T00:00:00",
                        "excludeTotalExpense": false
                    }
                    """;
            // When
            var response = ExpenseAPI.지출수정요청(1L, modifyBody, accessToken);

            // Then
            assertThat(response.response().statusCode()).isEqualTo(400);
        }

        @Test
        @DisplayName("실패 400 - 합계여부를 보내지 않는 경우")
        void successNullExcludeTotalExpense() {
            var body = """
                    {
                        "memo": "친구들이랑 점심 탕수육",
                        "categoryId": 1,
                        "expenseMount": 15000,
                        "expenseDateTime": "2023-01-01T00:00:00"
                    }
                    """;

            // When
            var response = ExpenseAPI.지출수정요청(1L, body, accessToken);
            JsonPath jsonPath = response.jsonPath();

            // Then
            assertThat(response.response().statusCode()).isEqualTo(400);
            assertThat(jsonPath.getString("code")).isEqualTo(ErrorType.E001.getCode());
            assertThat(jsonPath.getString("errorFields[0].field")).isEqualTo("excludeTotalExpense");
            assertThat(jsonPath.getString("errorFields[0].message")).contains("합계 제외 여부는 필수입니다.");
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
            var response = ExpenseAPI.지출수정요청(1L, body, accessToken);
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
            var response = ExpenseAPI.지출수정요청(1L, body, null);
            JsonPath jsonPath = response.jsonPath();

            // Then
            assertThat(response.response().statusCode()).isEqualTo(403);
            assertThat(jsonPath.getString("code")).isEqualTo(AuthenticationErrorType.INVALID_TOKEN.getCode());
            assertThat(jsonPath.getString("message")).isEqualTo(AuthenticationErrorType.INVALID_TOKEN.getMessage());
        }

    }

    private Expense findExpense() {
        return expenseRepository.findById(1L)
                .orElseThrow(ExpenseNotFoundException::new);
    }
}
