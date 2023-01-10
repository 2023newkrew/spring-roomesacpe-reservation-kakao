package nextstep.controller;

import io.restassured.RestAssured;
import nextstep.Theme;
import nextstep.domain.dto.CreateReservationDTO;
import nextstep.domain.reservation.Reservation;
import nextstep.domain.reservation.Reservations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.core.Is.is;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationTest {
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        DateTimeFormatter localDateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter localTimeformatter = DateTimeFormatter.ofPattern("HH:mm");
        Reservations.removeAll();
        Reservations.add(new Reservation(
                1L,
                LocalDate.parse("2022-08-11", localDateformatter),
                LocalTime.parse("13:00", localTimeformatter),
                "name",
                new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000)
        ));
    }

    @DisplayName("create reservation test")
    @Test
    void createReservation() {
        CreateReservationDTO reservationDto = new CreateReservationDTO("2022-08-11", "13:00", "name");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationDto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/reservations/2");
    }

    @DisplayName("get reservation test")
    @Test
    void getReservation() {

        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("id", is(1))
                .body("date", is("2022-08-11"))
                .body("time", is("13:00"))
                .body("name", is("name"))
                .body("themeName", is("워너고홈"))
                .body("themeDesc", is("병맛 어드벤처 회사 코믹물"))
                .body("themePrice", is(29_000));
    }

    @DisplayName("delete reservation test")
    @Test
    void deleteReservation() {
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
        assertThat(Reservations.get(1L)).isNull();
    }
}
