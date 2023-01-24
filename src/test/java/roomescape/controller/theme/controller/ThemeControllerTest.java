package roomescape.controller.theme.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Theme;
import roomescape.theme.dto.ThemeRequest;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class ThemeControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    JdbcTemplate jdbcTemplate;

    final static ThemeRequest DUMMY_THEME_REQUEST = new ThemeRequest(
            "테마이름1",
            "테마설명",
            22000
    );

    final static ThemeRequest DUMMY_THEME_REQUEST_2 = new ThemeRequest(
            "테마이름2",
            "테마설명",
            22000
    );

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    /**
     * RoomEscapeController > createReservation 메서드
     */
    @DisplayName("theme이 잘 생성되는지 확인한다")
    @Test
    void createTheme() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(DUMMY_THEME_REQUEST)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/themes/1");
    }

    /**
     * RoomEscapeController > createReservation 메서드
     */
    @DisplayName("중복되는 이름의 테마를 추가하려는 경우, 예외가 발생한다")
    @Test
    void duplicatedTheme() {
        createTheme();
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(DUMMY_THEME_REQUEST)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    /**
     * RoomEscapeController > lookUpReservation 메서드
     */
    @DisplayName("theme 객체들을 잘 가져오는지 확인한다")
    @Test
    void viewAllThemes() {
        createTheme();
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(DUMMY_THEME_REQUEST_2)
                .when().post("/themes");

        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    /**
     * RoomEscapeController > deleteReservation 메서드
     */
    @DisplayName("id에 해당하는 theme 객체를 잘 삭제하는지 확인한다")
    @Test
    void deleteTheme() {
        createTheme();
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/themes/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
