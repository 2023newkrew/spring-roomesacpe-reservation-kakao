package nextstep.main.java.nextstep.controller;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import nextstep.main.java.nextstep.domain.Reservation;
import nextstep.main.java.nextstep.domain.ReservationCreateRequestDto;
import nextstep.main.java.nextstep.service.ReservationService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {
    @LocalServerPort
    int port;
    ReservationCreateRequestDto request = new ReservationCreateRequestDto(
            LocalDate.of(2023, 1, 9),
            LocalTime.of(1, 30),
            "name"
    );
    @MockBean
    private ReservationService reservationService;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        RestAssured.registerParser("application/json", Parser.JSON);
    }

    @DisplayName("예약 생성 테스트")
    @Test
    void createReservation() {
        when(reservationService.save(request))
                .thenReturn(new Reservation(1L, LocalDate.of(2023, 1, 9), LocalTime.of(1, 30), "name", null));

        RestAssured.given()
                .log()
                .all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/reservations")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/reservations/1");
    }

    @DisplayName("예약 조회 테스트")
    @Test
    void findOneReservationTest() {
        when(reservationService.findOneById(1L))
                .thenReturn(new Reservation(1L, LocalDate.of(2023, 1, 9), LocalTime.of(1, 30), "name", null));

        RestAssured.given()
                .log()
                .all()
                .when()
                .get("/reservations/1")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.OK.value())
                .body("id", Matchers.equalTo(1));
    }

    @DisplayName("예약 삭제 테스트")
    @Test
    void deleteOneReservationTest() {
        RestAssured.given()
                .log()
                .all()
                .when()
                .delete("/reservations/1")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

}
