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

    final static Theme DUMMY_THEME = new Theme(
            1L,
            "테마이름",
            "테마설명",
            22000
    );

    final static Theme DUMMY_THEME_2 = new Theme(
            2L,
            "테마이름",
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
                .body(DUMMY_THEME)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/themes/1");
    }

    /**
     * RoomEscapeController > lookUpReservation 메서드
     */
    @DisplayName("theme 객체들을 잘 가져오는지 확인한다")
    @Test
    void findThemeById() {
        createTheme();
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(DUMMY_THEME_2)
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
