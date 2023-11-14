package jaringobi.acceptance.user;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.path.json.JsonPath;
import jaringobi.acceptance.ApiTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("[로그인] API 테스트 : /api/v1/login ")
public class UserLoginApiTest extends ApiTest {

    @Test
    @DisplayName("성공 200")
    void successLogin() {
        // Given
        String signUpbody = """
                    {
                        "username": "username123",
                        "password": "password123!"
                    }
                """;
        UserApi.회원가입요청(signUpbody);

        String loginBody = """
                {
                    "username": "username123",
                    "password": "password123!"
                }
                """;

        // When
        var response = UserApi.로그인요청(loginBody);
        JsonPath jsonPath = response.jsonPath();

        // Then
        assertThat(response.response().statusCode()).isEqualTo(200);
        assertThat(response.header("Content-Type")).isEqualTo("application/json");
        assertThat(jsonPath.getString("code")).isEqualTo("200");
        assertThat(jsonPath.getString("data.accessToken")).isNotNull();
        assertThat(jsonPath.getString("data.refreshToken")).isNotNull();
    }

    @Test
    @DisplayName("실패 404 - 존재하지 않는 계정명 로그인 시도")
    void failLoginNotFoundUser() {
        String loginBody = """
                {
                    "username": "username123231321",
                    "password": "password123!"
                }
                """;

        // When
        var response = UserApi.로그인요청(loginBody);

        // Then
        assertThat(response.response().statusCode()).isEqualTo(404);
        assertThat(response.header("Content-Type")).isEqualTo("application/json");
    }

    @Test
    @DisplayName("실패 400 - 일치하지 않는 패스워드")
    void failNotMatchesPassword() {
        // Given
        String signUpbody = """
                    {
                        "username": "username123",
                        "password": "password123!"
                    }
                """;
        UserApi.회원가입요청(signUpbody);

        String loginBody = """
                {
                    "username": "username123",
                    "password": "password123!1"
                }
                """;

        // When
        var response = UserApi.로그인요청(loginBody);

        // Then
        assertThat(response.response().statusCode()).isEqualTo(400);
        assertThat(response.header("Content-Type")).isEqualTo("application/json");
    }

    @Test
    @DisplayName("실패 400 - 비어있는 password")
    void failEmptyPassword() {
        String loginBody = """
                {
                    "username": "username123"
                }
                """;

        // When
        var response = UserApi.로그인요청(loginBody);
        JsonPath jsonPath = response.jsonPath();

        // Then
        assertThat(response.response().statusCode()).isEqualTo(400);
        assertThat(response.header("Content-Type")).isEqualTo("application/json");
        assertThat(jsonPath.getString("code")).contains("E001");
        assertThat(jsonPath.getString("message")).contains("필드 값 에러");
        assertThat(jsonPath.getString("errorFields[0].field")).contains("password");
        assertThat(jsonPath.getString("errorFields[0].message")).contains("필수입니다.");
    }

    @Test
    @DisplayName("실패 400 - 비어있는 username")
    void failNotEmptyUsername() {
        String loginBody = """
                {
                    "password": "password123!"
                }
                """;

        // When
        var response = UserApi.로그인요청(loginBody);
        JsonPath jsonPath = response.jsonPath();

        // Then
        assertThat(response.response().statusCode()).isEqualTo(400);
        assertThat(response.header("Content-Type")).isEqualTo("application/json");
        assertThat(jsonPath.getString("code")).contains("E001");
        assertThat(jsonPath.getString("message")).contains("필드 값 에러");
        assertThat(jsonPath.getString("errorFields[0].field")).contains("username");
        assertThat(jsonPath.getString("errorFields[0].message")).contains("필수입니다.");
    }

    @Test
    @DisplayName("실패 400 - username 과 password 가 둘 다 값이 비어있는 경우")
    void failEmptyUsernameAndPassword() {
        String loginBody = """
                {
                    "username": "",
                    "password": ""
                }
                """;

        // When
        var response = UserApi.로그인요청(loginBody);
        JsonPath jsonPath = response.jsonPath();

        // Then
        assertThat(response.response().statusCode()).isEqualTo(400);
        assertThat(response.header("Content-Type")).isEqualTo("application/json");
        assertThat(jsonPath.getString("code")).contains("E001");
        assertThat(jsonPath.getString("message")).contains("필드 값 에러");
    }

}
