package roomservice.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import roomservice.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.core.Is.is;


@DisplayName("Http Method")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationTest {
    private final static LocalDate testDate = LocalDate.of(2023, 1, 1);
    private final static LocalTime testTime = LocalTime.of(13, 0);
    private Reservation reservation;

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        Reservation reservation = new Reservation();
        reservation.setDate(testDate);
        reservation.setTime(testTime);
        reservation.setName("hi");
    }

    @DisplayName("Http Method - POST")
    @Test
    void createReservation() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/reservations/1");
    }

    @DisplayName("Http Method - GET")
    @Test
    void showReservation() {
        ReservationController reservationController = new ReservationController();
        reservationController.createReservation(reservation);
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("name", is("hi"));
    }


    @DisplayName("Http Method - DELETE")
    @Test
    void deleteReservation() {
        ReservationController reservationController = new ReservationController();
        reservationController.createReservation(reservation);
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
