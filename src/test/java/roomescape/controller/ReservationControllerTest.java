package roomescape.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import roomescape.dto.ReservationRequestDto;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.core.Is.is;

@Sql("/pretest.sql")
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
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto(LocalDate.now(), LocalTime.now(), "tester", 1L);
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationRequestDto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("이미 있는 일정 예약시 생성 실패 테스트")
    @Test
    void reservationFailTest() {
        LocalDate date = LocalDate.parse("2023-01-01");
        LocalTime time = LocalTime.parse("11:00:00");
        String name = "tester";
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto(date, time, name, 1L);
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationRequestDto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("예약 조회 테스트")
    @Test
    void reservationFindTest() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("date", is("2023-01-01"))
                .body("time", is("11:00:00"))
                .body("name", is("Tester"));
    }

    @DisplayName("예약 삭제 테스트")
    @Test
    void reservationDeleteTest() {
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
