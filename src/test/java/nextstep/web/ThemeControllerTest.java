package nextstep.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import nextstep.model.Theme;
import nextstep.repository.ThemeRepository;
import nextstep.web.dto.ThemeRequest;
import nextstep.web.dto.ThemeResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ThemeRepository themeRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @AfterEach
    void tearDown() {
        themeRepository.deleteAll();
    }

    @DisplayName("테마를 생성한다.")
    @Test
    void createTheme() {
        long id = 테마를_생성한다("name", "desc", 10000);

        assertThat(themeRepository.findAll())
                .extracting(Theme::getId, Theme::getName, Theme::getDesc, Theme::getPrice)
                .hasSize(1)
                .contains(tuple(id, "name", "desc", 10000));
    }

    @DisplayName("테마 목록을 조회한다.")
    @Test
    void getTheme() {
        long id1 = 테마를_생성한다("name1", "desc1", 10000);
        long id2 = 테마를_생성한다("name2", "desc2", 20000);

        List<ThemeResponse> actual = 테마를_조회한다();

        assertThat(actual)
                .extracting(ThemeResponse::getId, ThemeResponse::getName, ThemeResponse::getDesc, ThemeResponse::getPrice)
                .hasSize(2)
                .contains(
                        tuple(id1, "name1", "desc1", 10000),
                        tuple(id2, "name2", "desc2", 20000)
                );
    }

    @DisplayName("테마를 삭제한다.")
    @Test
    void deleteTheme() {
        long id = 테마를_생성한다("name", "desc", 10000);

        테마를_삭제한다(id);

        List<ThemeResponse> themes = 테마를_조회한다();
        Assertions.assertThat(themes).isEmpty();
    }

    private long 테마를_생성한다(String name, String desc, int price) {
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

    private List<ThemeResponse> 테마를_조회한다() {
        return RestAssured.given().log().all()
                .when().log().all()
                .get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body().jsonPath().getList(".", ThemeResponse.class);
    }

    private void 테마를_삭제한다(long id) {
        RestAssured.given().log().all()
                .when().log().all()
                .delete("/themes/" + id)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .extract();
    }
}
