package jaringobi.acceptance.user;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class UserApi {

    public static ExtractableResponse<Response> 회원가입요청(String body) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when()
                .post("/api/v1/signup")
                .andReturn()
                .then()
                .log().all().extract();
    }

    public static ExtractableResponse<Response> 로그인요청(String body) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when()
                .post("/api/v1/login")
                .andReturn()
                .then()
                .log().all().extract();
    }
}
