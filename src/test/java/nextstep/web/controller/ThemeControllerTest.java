package nextstep.web.controller;

import static org.hamcrest.core.Is.is;

import io.restassured.RestAssured;
import nextstep.domain.Theme;
import nextstep.web.exceptions.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("Theme Controller")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class ThemeControllerTest {
    private Theme theme;
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        theme = new Theme("테마이름", "테마설명", 22000);
    }

    @Order(1)
    @DisplayName("테마 생성")
    @Test
    void createThemeTest() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(theme)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/themes/1");
    }

    @Order(2)
    @DisplayName("테마 목록 조회")
    @Test
    void lookupAllThemesTest() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("get(0).name", is("테마이름"))
                .body("get(0).desc", is("테마설명"))
                .body("get(0).price", is(22000));

    }

    @Order(3)
    @DisplayName("테마 생성 시 이름이 똑같은 테마가 있다면 생성할 수 없음")
    @Test
    void themeExceptionTest() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(theme)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(is(ErrorCode.ALREADY_THEME_EXISTS.getMessage()));
    }

    @Order(4)
    @DisplayName("테마 삭제")
    @Test
    void deleteThemeTest() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/themes/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("name", is("테마이름"))
                .body("desc", is("테마설명"))
                .body("price", is(22000));
    }
}