package roomescape.controller.exception;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import roomescape.dto.Theme;

@DisplayName("예외 처리")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql("classpath:/test.sql")
public class ThemeControllerExceptionTest {

    private static final String NAME_DATA = "워너고홈";
    private static final String DESC_DATA = "병맛 어드벤처 회사 코믹물";
    private static final int PRICE_DATA = 29000;

    private static final String THEME_PATH = "/theme";
    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("테마 생성) content-type이 application/json이 아닌 경우 값을 받지 않는다.")
    @ParameterizedTest
    @ValueSource(strings = {
            MediaType.TEXT_PLAIN_VALUE,
            MediaType.TEXT_HTML_VALUE,
            MediaType.TEXT_XML_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    void failToPostNotJson(String contentType) {
        RestAssured.given().log().all()
                .contentType(contentType)
                .body("")
                .when().post(THEME_PATH)
                .then().log().all()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    @DisplayName("테마 생성) 같은 이름의 예약은 생성 불가")
    @Test
    void failToPostAlreadyExist() {
        Theme theme = new Theme(NAME_DATA, DESC_DATA, PRICE_DATA);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(theme)
                .when().post(THEME_PATH)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("테마 생성) 값의 포맷이 맞지 않을 경우 생성 불가")
    @ParameterizedTest
    @ValueSource(strings = {
            "{\"name\": \"TEST\",\"desc\": \"DESC\",\"price\": \"TEST\"}"})
    void failToPostInvalidFormat(String body) {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE).body(body)
                .when().post(THEME_PATH)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("테마 생성) 값이 포함되지 않았을 경우 생설 불가")
    @ParameterizedTest
    @ValueSource(strings = {
            "{\"desc\": \"DESC\",\"price\": 10000}",
            "{\"name\": \"TEST\"\"price\": 10000}",
            "{\"name\": \"TEST\",\"desc\": \"DESC\"}"})
    void failToPostNotExistingValue(String body) {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE).body(body)
                .when().post(THEME_PATH)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("테마 조회) ID가 없는 경우 조회 불가 및 ID가 잘못된 경우 (float, string)")
    @ParameterizedTest
    @ValueSource(strings = {
            THEME_PATH + "/10",
            THEME_PATH + "/1.1",
            THEME_PATH + "/test"})
    void failToGetNotExistingIdAndInvalidId(String path) {
        RestAssured.given().log().all()
                .when().get(path)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("테마 삭제) ID가 없는 경우 조회 불가 및 ID가 잘못된 경우 또는 예약과 관계있는 테마 (float, string)")
    @ParameterizedTest
    @ValueSource(strings = {
            THEME_PATH + "/10",
            THEME_PATH + "/1.1",
            THEME_PATH + "/test",
            THEME_PATH + "/2"})
    void failToDeleteNotExistingIdAndInvalidIdAndReservationThemeId(String path) {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(path)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
