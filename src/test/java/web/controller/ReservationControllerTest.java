package web.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import web.dto.request.ReservationRequestDTO;
import web.dto.request.ThemeRequestDTO;

import static org.hamcrest.core.Is.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservationControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("테마 생성을 성공하면 201 반환")
    @Test
    @Order(1)
    void createTheme() {
        ThemeRequestDTO themeRequestDTO = new ThemeRequestDTO("우주테마", "우주선을 몰아서 지구로 가는 테마", 40000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequestDTO)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/themes/1");
    }

    @DisplayName("예약 생성을 성공하면 201 반환")
    @Test
    @Order(2)
    void createReservation() {
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO("2022-08-11", "13:00", "name", 1L);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationRequestDTO)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/reservations/1");
    }

    @DisplayName("유효하지 않은 날짜로 예약 생성하면 400 반환")
    @Test
    void invalidDateTimeReservation() {
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO("2022-13-11", "13:00", "name", 1L);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationRequestDTO)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("시간대가 겹치는 예약을 하게되면 409 반환")
    @Test
    @Order(3)
    void duplicateDateTimeReservation() {
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO("2022-08-11", "13:00", "name", 1L);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationRequestDTO)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @DisplayName("예약 조회를 성공하면 200 반환")
    @Test
    @Order(3)
    void retrieveReservation() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body("id", is(1))
                .body("date", is("2022-08-11"))
                .body("time", is("13:00"))
                .body("name", is("name"))
                .body("themeName", is("우주테마"))
                .body("themeDesc", is("우주선을 몰아서 지구로 가는 테마"))
                .body("themePrice", is(40000));
    }

    @DisplayName("없는 예약 조회 시 404 반환")
    @Test
    void retrieveNotExistingReservation() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/99")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("예약 취소를 성공하면 204 반환")
    @Test
    @Order(4)
    void deleteReservation() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("없는 예약을 취소하면 404를 반환")
    @Test
    void deleteNotExistingReservation() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations/99")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
