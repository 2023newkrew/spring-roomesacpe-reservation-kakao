package nextstep.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.dto.CreateThemeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.equalTo;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ThemeAcceptanceTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 테마_생성_요청() {
        // given
        CreateThemeRequest themeRequest = new CreateThemeRequest("테마1", "테마내용입니다", 19000);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequest)

        // when
                .when().post("/themes")


        // then
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void id로_테마_조회() {
        // given
        CreateThemeRequest themeRequest = new CreateThemeRequest("테마1", "테마내용입니다", 19000);
        ExtractableResponse<Response> response = createTheme(themeRequest);
        String expected = response.header("Location").split("/")[2];
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)

        // when
                .when().get(response.header("Location"))

        // then
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(Integer.valueOf(expected)))
                .body("name", equalTo(themeRequest.getName()))
                .body("desc", equalTo(themeRequest.getDesc()))
                .body("price", equalTo(themeRequest.getPrice()));
    }

    @Test
    void 모든_테마_조회() {
        // given
        CreateThemeRequest themeRequest = new CreateThemeRequest("테마1", "테마내용입니다", 19000);
        createTheme(themeRequest);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)

        // when
                .when().get("/themes")

        // then
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    private ExtractableResponse<Response> createTheme(CreateThemeRequest themeRequest) {
        return RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequest)
                .when()
                .post("/themes")
                .then()
                .extract();
    }
}
