package roomescape.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import roomescape.dto.ReservationRequestDto;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }


    @DisplayName("예약 생성 성공 테스트")
    @Test
    void reservationSuccessTest() {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        String name = "tester";
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto(date, time, name);
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationRequestDto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }
}
