package nextstep.web.controller;

import io.restassured.RestAssured;
import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.web.VO.ReservationRequestVO;
import nextstep.web.exceptions.ErrorCode;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.core.Is.is;

@DisplayName("Reservation Controller")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class ReservationControllerTest {
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Order(1)
    @DisplayName("예약 생성")
    @Test
    void createReservationTest() {
        ReservationRequestVO reservationRequestVO = new ReservationRequestVO(
                LocalDate.of(2022, 8, 11),
                LocalTime.of(13, 0),
                "name",
                "테마이름");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new Theme("테마이름", "테마설명", 22000))
                .when().post("/themes");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationRequestVO)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/reservations/1");
    }

    @Order(2)
    @DisplayName("예약 조회")
    @Test
    void lookupReservationTest() {
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
                .body("themePrice", is(22000));

    }

    @Order(3)
    @DisplayName("예약 생성 시 날짜와 시간이 똑같은 예약이 있다면 예약을 생성할 수 없음")
    @Test
    void reservationExceptionTest() {
        Reservation reservation = new Reservation(LocalDate.of(2022, 8, 11), LocalTime.of(13, 0), "name");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(is(ErrorCode.ALREADY_RESERVATION_EXISTS.getMessage()));
    }

    @Order(4)
    @DisplayName("예약 삭제")
    @Test
    void deleteReservationTest() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", is(1))
                .body("date", is("2022-08-11"))
                .body("time", is("13:00"))
                .body("name", is("name"))
                .body("themeName", is("테마이름"))
                .body("themeDesc", is("테마설명"))
                .body("themePrice", is(22000));
    }
}