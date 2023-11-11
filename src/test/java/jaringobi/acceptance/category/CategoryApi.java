package jaringobi.acceptance.category;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class CategoryApi {

    public static ExtractableResponse<Response> 카테고리목록요청() {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/v1/categories")
                .andReturn()
                .then()
                .log().all().extract();
    }
}
