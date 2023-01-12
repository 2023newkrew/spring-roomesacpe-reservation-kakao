package nextstep.controller;

import io.restassured.RestAssured;
import nextstep.domain.ReservationSaveForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.core.Is.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationControllerTest {
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("예약 생성")
    @Test
    void createReservation() {
        ReservationSaveForm reservationSaveForm = new ReservationSaveForm(
                LocalDate.of(2022, 8, 11),
                LocalTime.of(15, 0),
                "name3",
                1L
        );

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationSaveForm)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", matchesPattern("/reservations/\\d+"));
    }

    @DisplayName("예약 생성 시 같은 시간대, 같은 테마에 예약이 있다면 예약을 생성할 수 없음")
    @Test
    void reservationException() {
        ReservationSaveForm reservationSaveForm = new ReservationSaveForm(
                LocalDate.of(2022, 8, 11),
                LocalTime.of(13, 0),
                "name3",
                1L
        );

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationSaveForm)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CONFLICT.value())
                .body(is("동일한 시간대, 동일한 테마에 예약이 이미 존재합니다."));
    }

    @DisplayName("예약 조회")
    @Test
    void lookupReservation() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", is(1))
                .body("date", is("2022-08-11"))
                .body("time", is("13:00"))
                .body("name", is("name"))
                .body("themeName", is("테마이름"))
                .body("themeDesc", is("테마설명"))
                .body("themePrice", is(22_000));
    }

    @DisplayName("존재하지 않는 예약 조회")
    @Test
    void reservationNotExistsWhenFind() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/10")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body(is("예약이 존재하지 않습니다."));
    }

    @DisplayName("예약 삭제")
    @Test
    void deleteReservation() {
        RestAssured.given().log().all()
                .when().delete("/reservations/2")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("존재하지 않는 예약 삭제")
    @Test
    void reservationNotExistsWhenDelete() {
        RestAssured.given().log().all()
                .when().delete("/reservations/10")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body(is("예약이 존재하지 않습니다."));
    }
}