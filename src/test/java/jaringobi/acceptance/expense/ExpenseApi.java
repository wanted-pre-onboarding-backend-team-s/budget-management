package jaringobi.acceptance.expense;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class ExpenseApi {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    public static ExtractableResponse<Response> 지출추가요청(String body, String token) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .header(AUTHORIZATION, BEARER + token)
                .body(body)
                .post("/api/v1/expenditures")
                .andReturn()
                .then()
                .log().all().extract();
    }
}
