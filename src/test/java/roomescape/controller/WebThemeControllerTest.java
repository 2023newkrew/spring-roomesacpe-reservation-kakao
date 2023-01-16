package roomescape.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import roomescape.domain.Theme;

import java.util.Objects;

@DisplayName("Theme Test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(value = {AcceptanceTestExecutionListener.class,}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public class WebThemeControllerTest {
    @LocalServerPort
    int port;

    Theme theme;
    @Autowired
    ThemeController themeController;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        theme = new Theme(0L,
                "testName",
                "description",
                10000);
    }

    @DisplayName("테마 등록이 가능함")
    @Test
    void createThemeTest() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(theme)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("테마가 등록되었다면 조회할 수 있음")
    @Test
    void showReservationTest() {
        String createBody = themeController.createTheme(theme).getBody();
        String reserveId = Objects.requireNonNull(createBody).split("/")[2];
        RestAssured.given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/themes/" + reserveId)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("테마가 등록되었다면 취소할 수 있음")
    @Test
    void deleteReservationTest() {
        String createBody = themeController.createTheme(theme).getBody();
        String deleteId = Objects.requireNonNull(createBody).split("/")[2];
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/themes/" + deleteId)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("동일한 이름/가격의 테마가 등록된 경우, 예외가 발생")
    @Test
    void duplicatedReservationTest(){
        themeController.createTheme(theme);
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(theme)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("등록되지 않은 ID를 조회할 경우, 예외가 발생")
    @Test
    void notFoundThemeTest(){
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(theme)
                .when().get("/themes/"+ 12121L)
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}

