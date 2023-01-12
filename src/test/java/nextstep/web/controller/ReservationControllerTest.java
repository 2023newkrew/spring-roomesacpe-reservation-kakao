package nextstep.web.controller;

import io.restassured.RestAssured;
import nextstep.web.reservation.dto.CreateReservationRequestDto;
import nextstep.web.reservation.dto.CreateReservationResponseDto;
import nextstep.web.reservation.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    ReservationService reservationService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 예약을_생성할_수_있다() {
        CreateReservationRequestDto requestDto = new CreateReservationRequestDto(
                LocalDate.of(2023, 1, 10), LocalTime.of(13, 0), "reservation1", 1L
        );

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 예약을_조회할_수_있다() {
        CreateReservationRequestDto requestDto = new CreateReservationRequestDto(
                LocalDate.of(2023, 1, 10), LocalTime.of(13, 0), "reservation1", 1L
        );
        CreateReservationResponseDto responseDto = reservationService.createReservation(requestDto);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(responseDto.getLocation())
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 예약을_취소할_수_있다() {
        CreateReservationRequestDto requestDto = new CreateReservationRequestDto(
                LocalDate.of(2023, 1, 10), LocalTime.of(13, 0), "reservation1", 1L
        );
        CreateReservationResponseDto responseDto = reservationService.createReservation(requestDto);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(responseDto.getLocation())
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }
}
