package roomescape.controller;

import static org.hamcrest.core.Is.is;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import roomescape.dto.Theme;

@DisplayName("테마 웹 요청 / 응답 처리")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql("classpath:/test.sql")
public class ThemeControllerTest {

    private static final String NAME_DATA1 = "워너고홈";
    private static final String NAME_DATA2 = "테스트 이름";
    private static final String DESC_DATA = "병맛 어드벤처 회사 코믹물";
    private static final int PRICE_DATA = 29000;

    private static final String THEME_PATH = "/theme";
    private static final String FIRST_THEME_PATH = THEME_PATH + "/1";

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("테마 생성")
    @Test
    void createReservation() {
        Theme theme = new Theme(NAME_DATA2, DESC_DATA, PRICE_DATA);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(theme)
                .when().post(THEME_PATH)
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("테마 조회")
    @Test
    void showReservation() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(THEME_PATH)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(2))
                .body("[0].name", is( NAME_DATA1))
                .body("[0].desc", is(DESC_DATA))
                .body("[0].price", is(PRICE_DATA));
    }

    @DisplayName("테마 취소")
    @Test
    void deleteReservation() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(FIRST_THEME_PATH)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
