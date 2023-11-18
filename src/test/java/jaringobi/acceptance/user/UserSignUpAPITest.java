package jaringobi.acceptance.user;


import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.path.json.JsonPath;
import jaringobi.acceptance.APITest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[회원가입] API 테스트 : /api/v1/signup ")
public class UserSignUpAPITest extends APITest {


    @Test
    @DisplayName("성공 200")
    void successSignUp() {
        String body = """
                    {
                        "username": "username123",
                        "password": "password123!"
                    }
                """;
        var response = UserAPI.회원가입요청(body);
        assertThat(response.response().statusCode()).isEqualTo(204);
        assertThat(response.header("Content-Type")).isEqualTo("application/json");
    }

    @Test
    @DisplayName("실패 409 - 이미 사용하고 있는 유저 아이디로 회원가입 시도 시")
    void failSignUp() {
        // given
        String body = """
                    {
                        "username": "username123",
                        "password": "password123!"
                    }
                """;
        UserAPI.회원가입요청(body);

        // when
        String body2 = """
                    {
                        "username": "username123",
                        "password": "password123!"
                    }
                """;
        var response = UserAPI.회원가입요청(body2);
        JsonPath jsonPath = response.jsonPath();

        // then
        assertThat(response.response().statusCode()).isEqualTo(409);
        assertThat(response.header("Content-Type")).isEqualTo("application/json");
        assertThat(jsonPath.getString("code")).isEqualTo("U001");
        assertThat(jsonPath.getString("message")).isEqualTo("이미 존재하는 계정명입니다.");
    }

    @Test
    @DisplayName("실패 400 - 계정명(username) 이 Null 이거나 Empty")
    void failUsernameError() {
        String body = """
                    {
                        "username": "",
                        "password": "password123!"
                    }
                """;
        var response = UserAPI.회원가입요청(body);
        JsonPath jsonPath = response.jsonPath();
        assertThat(response.response().statusCode()).isEqualTo(400);
        assertThat(response.header("Content-Type")).isEqualTo("application/json");
        assertThat(jsonPath.getString("code")).isEqualTo("E001");
        assertThat(jsonPath.getString("message")).isEqualTo("필드 값 에러");
    }

    @Test
    @DisplayName("실패 400 - 비밀번호(password) 가 Null 이거나 Empty")
    void failPasswordError() {
        String body = """
                    {
                        "username": "usrename123",
                        "password": "password123"
                    }
                """;
        var response = UserAPI.회원가입요청(body);
        JsonPath jsonPath = response.jsonPath();

        assertThat(response.response().statusCode()).isEqualTo(400);
        assertThat(response.header("Content-Type")).isEqualTo("application/json");
        assertThat(jsonPath.getString("code")).isEqualTo("E001");
        assertThat(jsonPath.getString("message")).isEqualTo("필드 값 에러");
    }

}
