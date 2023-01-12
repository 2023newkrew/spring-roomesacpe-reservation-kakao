package web.presentation;

import static org.hamcrest.core.Is.is;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import web.dto.request.ReservationRequestDTO;
import web.exception.DuplicatedReservationException;
import web.exception.NoSuchReservationException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class ReservationControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("Reservation 생성")
    @Test
    @Order(1)
    void createReservation() {
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO("2022-08-11", "13:00", "name");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationRequestDTO)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/reservations/1");
    }

    @DisplayName("유효하지 않은 Reservation 생성은 400을 반환한다.")
    @Test
    void invalidDateTimeReservation() {
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO("2022-13-11", "13:00", "name");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationRequestDTO)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", is("날짜 형식이 일치하지 않습니다."));
    }

    @DisplayName("시간대가 겹치는 예약을 하게되면 409를 반환한다.")
    @Test
    @Order(3)
    void duplicateDateTimeReservation() {
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO("2022-08-11", "13:00", "name");
        final DuplicatedReservationException e = new DuplicatedReservationException();

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationRequestDTO)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CONFLICT.value())
                .body("message", is(e.getMessage()));
    }

    @DisplayName("Reservation 조회")
    @Test
    @Order(2)
    void retrieveReservation() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body("id", is(1))
                .body("date", is("2022-08-11"))
                .body("time", is("13:00"))
                .body("name", is("name"))
                .body("themeName", is("워너고홈"))
                .body("themeDesc", is("병맛 어드벤처 회사 코믹물"))
                .body("themePrice", is(29000));
    }

    @DisplayName("없는 Reservation 조회 시 404를 반환한다.")
    @Test
    void retrieveNotExistingReservation() {
        final NoSuchReservationException e = new NoSuchReservationException();

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/15")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", is(e.getMessage()));
    }

    @DisplayName("Reservation 취소")
    @Test
    @Order(4)
    void deleteReservation() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("없는 예약을 취소하면 404를 반환한다.")
    @Test
    void deleteNotExistingReservation() {
        final NoSuchReservationException e = new NoSuchReservationException();

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations/15")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", is(e.getMessage()));
    }
}
