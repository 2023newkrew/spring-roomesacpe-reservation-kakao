package nextstep.web.controller;

import io.restassured.RestAssured;
import nextstep.web.dto.ThemeRequestDto;
import nextstep.web.service.ThemeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.startsWith;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ThemeControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    ThemeService themeService;

    ThemeRequestDto requestDto;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        requestDto = new ThemeRequestDto(
                "테마이름", "테마설명", 1000
        );
    }

    @Test
    @Transactional
    void 테마를_생성할_수_있다() {
        RestAssured.given()
                .log()
                .all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDto)
                .when()
                .post("/themes")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", startsWith("/themes"));
    }

    @Test
    void 테마를_조회할_수_있다() {
        RestAssured.given()
                .log()
                .all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/themes/")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 테마를_삭제할_수_있다() {
        Long createdId = themeService.create(requestDto);

        RestAssured.given()
                .log()
                .all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/themes/" + createdId)
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
