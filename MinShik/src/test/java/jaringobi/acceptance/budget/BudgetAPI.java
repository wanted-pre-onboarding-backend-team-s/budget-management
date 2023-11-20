package jaringobi.acceptance.budget;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class BudgetAPI {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    public static ExtractableResponse<Response> 예산설정(String body, String token) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .header(AUTHORIZATION, BEARER + token)
                .body(body)
                .post("/api/v1/budget")
                .andReturn()
                .then()
                .log().all().extract();
    }

}
