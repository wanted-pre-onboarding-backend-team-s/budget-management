package jaringobi.acceptance.budget;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class BudgetApi {

    public static ExtractableResponse<Response> 예산설정(String body) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .body(body)
                .post("/api/v1/budget")
                .andReturn()
                .then()
                .log().all().extract();
    }

}
