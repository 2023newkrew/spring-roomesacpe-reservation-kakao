package roomescape.controller.reservation.controller;

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
import roomescape.reservation.dto.ReservationRequest;
import roomescape.theme.dto.ThemeRequest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class ReservationControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    JdbcTemplate jdbcTemplate;

    final static ReservationRequest DUMMY_RESERVATION_REQUEST =
            new ReservationRequest(
                    LocalDate.of(2022, 8, 11),
                    LocalTime.of(13, 0, 0),
                    "name22",
                    1L);
    final static ThemeRequest DUMMY_THEME_REQUEST = new ThemeRequest(
            "테마이름",
            "테마설명",
            22000
    );

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(DUMMY_THEME_REQUEST)
                .when().post("/themes");
    }

    /**
     * ReservationController > createReservation 메서드
     */
    @DisplayName("reservation이 잘 생성되는지 확인한다")
    @Test
    void createReservation() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(DUMMY_RESERVATION_REQUEST)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/reservations/1");
    }

    /**
     * ReservationController > createReservation 메서드
     */
    @DisplayName("동일한 날짜/시간대에 예약을 하는 경우, 예외가 발생한다")
    @Test
    void duplicatedReservation() {
        createReservation();
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(DUMMY_RESERVATION_REQUEST)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    /**
     * ReservationController > createReservation 메서드
     */
    @DisplayName("타임테이블에 해당하지 않는 시간에 예약을 할 경우, 예외가 발생한다")
    @Test
    void invalidTimeReservation() {
        ReservationRequest invalidReservationRequest =
                new ReservationRequest(
                        LocalDate.of(2022, 8, 11),
                        LocalTime.of(13, 2, 0),
                        "name22",
                        1L);
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(invalidReservationRequest)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    /**
     * ReservationController > createReservation 메서드
     */
    @DisplayName("없는 테마에 대해 예약할 경우, 예외가 발생한다")
    @Test
    void noSuchThemeReservation() {
        ReservationRequest invalidReservationRequest =
                new ReservationRequest(
                        LocalDate.of(2022, 8, 11),
                        LocalTime.of(13, 0, 0),
                        "name22",
                        2L);
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(invalidReservationRequest)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    /**
     * ReservationController > findReservationById 메서드
     */
    @DisplayName("id에 해당하는 reservation 객체를 잘 가져오는지 확인한다")
    @Test
    void findReservationById() {
        createReservation();
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", is(1));
    }

    /**
     * ReservationController > findReservationById 메서드
     */
    @DisplayName("id에 해당하는 reservation 객체가 없는 경우, 예외가 발생한다")
    @Test
    void noSuchReservation() {
        createReservation();
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/2")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    /**
     * ReservationController > deleteReservation 메서드
     */
    @DisplayName("id에 해당하는 reservation 객체를 잘 삭제하는지 확인한다")
    @Test
    void deleteReservation() {
        createReservation();
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    /**
     * ReservationController > deleteReservation 메서드
     */
    @DisplayName("없는 예약을 삭제하는 경우, 예외가 발생한다")
    @Test
    void deleteNotExistenceReservation() {
        createReservation();
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations/2")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
