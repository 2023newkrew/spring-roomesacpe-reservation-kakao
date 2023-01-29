package nextstep.controller;

import io.restassured.RestAssured;
import nextstep.domain.dto.ThemeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.Matchers.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ThemeControllerTest {
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("can POST theme")
    @Test
    void can_POST_theme() {
        ThemeRequest dto = new ThemeRequest(
                "test name",
                "test desc",
                10000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(dto)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/themes/1");
    }

    @DisplayName("can GET all themes")
    @Test
    void can_GET_all_themes() {
        ThemeRequest dto = new ThemeRequest(
                "test name",
                "test desc",
                10000);

        int CYCLE = 17;
        for (int i = 0; i < CYCLE; i++) {
            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(dto)
                    .when().post("/themes");
        }

        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("size()", is(CYCLE));
    }

    @DisplayName("can DELETE theme of given id")
    @Test
    void can_DELETE_theme_of_given_id() {
        this.can_POST_theme();

        RestAssured.given().log().all()
                .when().delete("/themes/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("can reject DELETE of nonexistent id")
    @Test
    void can_reject_DELETE_of_nonexistent_id() {
        RestAssured.given().log().all()
                .when().delete("/themes/1")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
