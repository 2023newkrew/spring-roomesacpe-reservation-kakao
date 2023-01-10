package nextstep.controller;

import io.restassured.RestAssured;
import nextstep.Reservation;
import nextstep.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.core.Is.is;


@DisplayName("Reservation Test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    ReservationService reservationService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("Reservation - 예약하기")
    @Test
    void reserve() {
        Reservation reservation = new Reservation(
                null,
                LocalDate.parse("2023-01-10"),
                LocalTime.parse("13:00:00"),
                "jay",
                null
        );

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/reservations/1");
    }

    @DisplayName("Reservation - 조회하기")
    @Test
    void show() {
        Reservation reservation = new Reservation(
                null,
                LocalDate.parse("2023-01-10"),
                LocalTime.parse("13:00:00"),
                "jay",
                null
        );
        reservationService.reserve(reservation);

        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", is(1))
                .body("date", is("2023-01-10"))
                .body("time", is("13:00:00"))
                .body("name", is("jay"))
                .body("themeName", is("워너고홈"))
                .body("themeDesc", is("병맛 어드벤처 회사 코믹물"))
                .body("themePrice", is(29000));
    }
}