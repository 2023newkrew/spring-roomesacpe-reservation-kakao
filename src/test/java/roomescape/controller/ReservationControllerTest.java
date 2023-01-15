package roomescape.controller;

import static org.hamcrest.core.Is.is;

import io.restassured.RestAssured;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import roomescape.dto.Reservation;


@DisplayName("예약 웹 요청 / 응답 처리")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql("classpath:/test.sql")
public class ReservationControllerTest {

    private static final LocalDate DATE_DATA1 = LocalDate.parse("2022-08-01");
    private static final LocalDate DATE_DATA2 = LocalDate.parse("2022-08-02");
    private static final LocalTime TIME_DATA = LocalTime.parse("13:00");
    private static final String NAME_DATA = "test";
    private static final Long THEME_ID_DATA = 1L;

    private static final String SECOND_STRING = ":00";

    private static final String RESERVATIONS_PATH = "/reservations";
    private static final String FIRST_RESERVATION_PATH = RESERVATIONS_PATH + "/1";

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("예약 생성")
    @Test
    void postReservation() {
        Reservation reservation = new Reservation(DATE_DATA2, TIME_DATA, NAME_DATA, THEME_ID_DATA);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservation)
                .when().post(RESERVATIONS_PATH)
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("예약 조회")
    @Test
    void getReservation() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(FIRST_RESERVATION_PATH)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("name", is(NAME_DATA))
                .body("date", is(DATE_DATA1.toString()))
                .body("time", is(TIME_DATA + SECOND_STRING));
    }

    @DisplayName("예약 취소")
    @Test
    void deleteReservation() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(FIRST_RESERVATION_PATH)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
