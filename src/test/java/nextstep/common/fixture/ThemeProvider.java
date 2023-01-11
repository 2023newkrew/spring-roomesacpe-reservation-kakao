package nextstep.common.fixture;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.dto.request.CreateThemeRequest;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;

public class ThemeProvider {

    public static ExtractableResponse<Response> 테마_생성을_요청한다(CreateThemeRequest createThemeRequest) {
        return given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createThemeRequest)
        .when()
                .post("/themes")
        .then()
                .extract();
    }

}
