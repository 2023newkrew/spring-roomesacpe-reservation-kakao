package nextstep.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import nextstep.web.dto.ThemeRequest;
import nextstep.web.dto.ThemeResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ThemeControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("테마를 생성한다.")
    @Test
    void createTheme() {
        ThemeRequest request = new ThemeRequest("name", "desc", 10000);

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().log().all()
                .post("/themes")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("테마 목록을 조회한다.")
    @Test
    void getTheme() {
        long id1 = createTheme("name1", "desc1", 10000);
        long id2 = createTheme("name2", "desc2", 20000);

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().log().all()
                .get("/themes")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        List<ThemeResponse> themes = response.body().jsonPath().getList(".", ThemeResponse.class);
        assertThat(themes)
                .extracting(ThemeResponse::getId, ThemeResponse::getName, ThemeResponse::getDesc, ThemeResponse::getPrice)
                .contains(
                        tuple(id1, "name1", "desc1", 10000),
                        tuple(id2, "name2", "desc2", 20000)
                );
    }

    @DisplayName("테마를 삭제한다.")
    @Test
    void deleteTheme() {
        long id = createTheme("name", "desc", 10000);

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().log().all()
                .delete("/themes/" + id)
                .then().log().all()
                .extract();

        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());

        List<ThemeResponse> themes = RestAssured.given().log().all()
                .when().log().all()
                .get("/themes")
                .then().log().all()
                .extract()
                .body()
                .jsonPath()
                .getList(".", ThemeResponse.class);

        Assertions.assertThat(themes).isEmpty();
    }

    private static long createTheme(String name, String desc, int price) {
        ThemeRequest request = new ThemeRequest(name, desc, price);

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().log().all()
                .post("/themes")
                .then().log().all()
                .extract();

        String id = response.header(HttpHeaders.LOCATION).split("/")[2];
        return Long.parseLong(id);
    }
}
