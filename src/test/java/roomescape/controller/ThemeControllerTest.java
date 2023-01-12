package roomescape.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import roomescape.dto.ThemeRequestDto;

import static org.hamcrest.core.Is.is;

@Sql("/pretest.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ThemeControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("테마 생성 성공 테스트")
    @Test
    void themeCreateSuccessTest() {
        ThemeRequestDto themeRequestDto = new ThemeRequestDto("테마마", "기타등등", 20000);
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequestDto)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("동일 이름 테마 생성 실패 테스트")
    @Test
    void themeCreateFailTest() {
        ThemeRequestDto themeRequestDto = new ThemeRequestDto("Test Theme", "기타등등", 20000);
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequestDto)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("테마 조회 테스트")
    @Test
    void reservationFindTest() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/themes/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("name", is("Test Theme"))
                .body("desc", is("Lorem Ipsum"))
                .body("price", is(10000));
    }

    @DisplayName("예약 있는 테마 삭제 실패 테스트")
    @Test
    void reservationDeleteTest() {
        RestAssured.given().log().all()
                .when().delete("/themes/2")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("예약 있는 테마 삭제 실패 테스트")
    @Test
    void reservationDeleteFailTest() {
        RestAssured.given().log().all()
                .when().delete("/themes/1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
