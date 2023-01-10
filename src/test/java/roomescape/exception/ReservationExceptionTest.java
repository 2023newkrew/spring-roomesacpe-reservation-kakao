package roomescape.exception;

import io.restassured.RestAssured;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import roomescape.dto.Reservation;
import roomescape.dto.Theme;

@DisplayName("예외 처리")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationExceptionTest {

    private static final Theme THEME = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("content-type이 application/json이 아닌 경우 값을 받지 않는다.")
    @ParameterizedTest
    @ValueSource(strings = {MediaType.TEXT_PLAIN_VALUE, MediaType.TEXT_HTML_VALUE,
            MediaType.TEXT_XML_VALUE, MediaType.APPLICATION_XML_VALUE})
    void notJson(String contentType) {
        RestAssured.given().log().all().contentType(contentType).body("").when()
                .post("/reservations").then().log().all()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    @DisplayName("예약 생성) 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다.")
    @Test
    void failToCreateReservationAlreadyExist() {
        Reservation reservation = new Reservation(null, LocalDate.parse("2022-08-10"),
                LocalTime.parse("14:00:00"), "name", THEME);

        RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(reservation).when()
                .post("/reservations").then().log().all().statusCode(HttpStatus.CREATED.value());

        RestAssured.given().log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservation).when().post("/reservations").then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약 생성) 값이 포함되지 않았을 경우 예약 생설 불가.")
    @ParameterizedTest
    @ValueSource(strings = {"{\"name\":\"abc\",\"time\":\"13:00:00\"}",
            "{\"name\":\"abc\",\"date\":\"2022-08-12\"}",
            "{\"date\":\"2022-08-12\",\"time\":\"13:00:00\"}",})
    void notContainRequiredField(String body) {
        RestAssured.given().log().all().contentType(MediaType.APPLICATION_JSON_VALUE).body(body)
                .when().post("/reservations").then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약 생성) 값의 포맷이 맞지 않을 경우 생성 불가")
    @ParameterizedTest
    @ValueSource(strings = {"{\"name\":\"abc\",\"date\":\"2022-08\",\"time\":\"13:00:00\"}",
            "{\"name\":\"abc\",\"date\":\"2022-08-09\",\"time\":\"13\"}",
            "{\"name\":\"abc\",\"date\":\"test\",\"time\":\"13:00:00\"}",
            "{\"name\":\"abc\",\"date\":\"2022-08-09\",\"time\":13}"})
    void isNotValidField(String body) {
        RestAssured.given().log().all().contentType(MediaType.APPLICATION_JSON_VALUE).body(body)
                .when().post("/reservations").then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약 조회) ID가 없는 경우 조회 불가")
    @Test
    void notExistID() {
        RestAssured.given().log().all().when().get("/reservations/-1").then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약 조회) ID가 잘못된 경우 (float, string)")
    @ParameterizedTest
    @ValueSource(strings = {"/reservations/test", "/reservations/1.1"})
    void failToGetWithInvalidId(String path) {
        RestAssured.given().log().all().contentType(MediaType.APPLICATION_JSON_VALUE).when()
                .get(path).then().log().all().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약 삭제) ID가 없는 경우 조회 불가")
    @Test
    void deleteNotExistId() {
        RestAssured.given().log().all().when().delete("/reservations/-1").then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약 삭제) ID가 잘못된 경우 (float, string)")
    @ParameterizedTest
    @ValueSource(strings = {"/reservations/test", "/reservations/1.1"})
    void failToDeleteWithInvalidId(String path) {
        RestAssured.given().log().all().contentType(MediaType.APPLICATION_JSON_VALUE).when()
                .delete(path).then().log().all().statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
