package nextstep.exception;

import io.restassured.RestAssured;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import nextstep.domain.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("예외 처리")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservationExceptionTest {

    @LocalServerPort
    int port;

    private static Stream<Arguments> getCreateReservationData() {
        return Stream.of(Arguments.of("2022-08-11", "13:00", "name"));
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("content-type이 application/json이 아닌 경우 값을 받지 않는다.")
    @Order(1)
    @ParameterizedTest
    @ValueSource(strings = {MediaType.TEXT_PLAIN_VALUE, MediaType.TEXT_HTML_VALUE,
            MediaType.TEXT_XML_VALUE, MediaType.APPLICATION_XML_VALUE})
    void notJson(String contentType) {
        RestAssured.given().log().all().contentType(contentType).body("").when()
                .post("/reservations").then().log().all()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    }

    @DisplayName("예약 생성) 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다.")
    @Order(2)
    @Test
    void failToCreateReservationAlreadyExist() {
        Reservation reservation = new Reservation(null, LocalDate.parse("2022-08-11"),
                LocalTime.parse("13:00:00"), "name", null);

        RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(reservation).when()
                .post("/reservations").then().log().all().statusCode(HttpStatus.CREATED.value())
                .header("Location", "/reservations/1");

        RestAssured.given().log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservation).when().post("/reservations").then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약 생성) 값이 포함되지 않았을 경우 예약 생설 불가.")
    @Order(3)
    @ParameterizedTest
    @ValueSource(strings = {"{\"name\":\"abc\",\"time\":\"13:00:00\"}",
            "{\"name\":\"abc\",\"date\":\"2022-08-12\"}",
            "{\"date\":\"2022-08-12\",\"time\":\"13:00:00\"}",})
    void notContainRequiredField(String body) {
        RestAssured.given().log().all().contentType(MediaType.APPLICATION_JSON_VALUE).body(body)
                .when().post("/reservations").then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
