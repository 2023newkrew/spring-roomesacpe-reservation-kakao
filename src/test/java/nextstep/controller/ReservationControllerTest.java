package nextstep.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import nextstep.RoomEscapeWebApplication;
import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.dto.ReservationRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RoomEscapeWebApplication.class)
class ReservationControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    private ReservationRequestDto generateReservationRequestDto(String date, String time, String name) {
        return new ReservationRequestDto(
                LocalDate.parse(date),
                LocalTime.parse(time),
                name
        );
    }

    @DisplayName("예약 생성 요청이 성공하면 201 코드와 Location 헤더 반환")
    @Test
    void reserveRequest() {
        ReservationRequestDto reservationRequestDto =
                generateReservationRequestDto("2023-01-01", "13:00", "john");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationRequestDto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/reservations/1");
    }
}
