package roomescape.controller.exception;

import io.restassured.RestAssured;
import java.time.LocalDate;
import java.time.LocalTime;
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
import roomescape.dto.Reservation;

@DisplayName("예외 처리")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql("classpath:/test.sql")
public class ReservationControllerExceptionTest {

    private static final LocalDate DATE_DATA1 = LocalDate.parse("2022-08-01");
    private static final LocalDate DATE_DATA2 = LocalDate.parse("2022-08-02");
    private static final LocalTime TIME_DATA = LocalTime.parse("13:00");
    private static final String NAME_DATA = "test";
    private static final Long THEME_ID_DATA = 1L;

    private static final String RESERVATIONS_PATH = "/reservations";

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("예약 생성) content-type이 application/json이 아닌 경우 값을 받지 않는다.")
    @ParameterizedTest
    @ValueSource(strings = {
            MediaType.TEXT_PLAIN_VALUE,
            MediaType.TEXT_HTML_VALUE,
            MediaType.TEXT_XML_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    void failToPostIfNotJson(String contentType) {
        RestAssured.given().log().all()
                .contentType(contentType).body("")
                .when().post(RESERVATIONS_PATH)
                .then().log().all()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    @DisplayName("예약 생성) 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다.")
    @Test
    void failToCreateReservationAlreadyExist() {
        Reservation reservation = new Reservation(DATE_DATA1, TIME_DATA, NAME_DATA, THEME_ID_DATA);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservation)
                .when().post(RESERVATIONS_PATH)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약 생성) 값이 포함되지 않았을 경우 예약 생설 불가.")
    @ParameterizedTest
    @ValueSource(strings = {
            "{\"name\":\"abc\",\"time\":\"13:00:00\"}",
            "{\"name\":\"abc\",\"date\":\"2022-08-12\"}",
            "{\"date\":\"2022-08-12\",\"time\":\"13:00:00\"}",})
    void notContainRequiredField(String body) {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE).body(body)
                .when().post(RESERVATIONS_PATH)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약 생성) 값의 포맷이 맞지 않을 경우 생성 불가")
    @ParameterizedTest
    @ValueSource(strings = {
            "{\"date\": [2022,8],\"time\": [13,0],\"name\": \"test\",\"themeId\": 1}",
            "{\"date\": [2022,8,2],\"time\": [13],\"name\": \"test\",\"themeId\": 1}",
            "{\"date\": [2022,8,2],\"time\": [13,0],\"name\": \"test\",\"themeId\": \"test\"}"})
    void isInValidFormat(String body) {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE).body(body)
                .when().post(RESERVATIONS_PATH)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약 생성) 값이 포함되지 않았을 경우 예약 생설 불가")
    @ParameterizedTest
    @ValueSource(strings = {
            "{\"time\": [13,0],\"name\": \"test\",\"themeId\": 1}",
            "{\"date\": [2022,8,2],\"name\": \"test\",\"themeId\": 1}",
            "{\"date\": [2022,8,2],\"time\": [13,0],\"themeId\": 1}",
            "{\"date\": [2022,8,2],\"time\": [13,0],\"name\": \"test\"}"})
    void isNotExistingValue(String body) {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE).body(body)
                .when().post(RESERVATIONS_PATH)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약 생성) 존재하지 않는 테마를 지정할 경우 생성 불가")
    @Test
    void createReservation() {
        Reservation reservation = new Reservation(DATE_DATA2, TIME_DATA, NAME_DATA, 3L);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservation)
                .when().post(RESERVATIONS_PATH)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약 조회) ID가 없는 경우 조회 불가 및 ID가 잘못된 경우 (float, string)")
    @ParameterizedTest
    @ValueSource(strings = {
            RESERVATIONS_PATH + "/10",
            RESERVATIONS_PATH + "/1.1",
            RESERVATIONS_PATH + "/test"})
    void notExistID(String path) {
        RestAssured.given().log().all()
                .when().get(path)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약 삭제) ID가 없는 경우 조회 불가 및 ID가 잘못된 경우 (float, string)")
    @ParameterizedTest
    @ValueSource(strings = {
            RESERVATIONS_PATH + "/10",
            RESERVATIONS_PATH + "/1.1",
            RESERVATIONS_PATH + "/test"})
    void failToDeleteWithInvalidId(String path) {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(path)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
