package nextstep.controller;

import io.restassured.RestAssured;
import nextstep.domain.Theme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ThemeControllerTest {
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("테마 생성")
    @Test
    void createTheme() {
        Theme theme = new Theme("테마이름3", "테마설", 22_000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(theme)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", matchesPattern("/themes/\\d+"));
    }

    @DisplayName("전체 테마 조회")
    @Test
    void showAllTheme() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("[0].id", notNullValue())
                .body("[0].name", notNullValue())
                .body("[0].desc", notNullValue())
                .body("[0].price", notNullValue());
    }

    @DisplayName("테마 삭제")
    @Test
    void deleteTheme() {
        RestAssured.given().log().all()
                .when().delete("/themes/2")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("존재하지 않는 테마 삭제")
    @Test
    void reservationNotExistsWhenDelete() {
        RestAssured.given().log().all()
                .when().delete("/themes/10")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body(is("테마가 존재하지 않습니다."));
    }
}