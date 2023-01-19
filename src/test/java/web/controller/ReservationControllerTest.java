package web.controller;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import web.dto.request.ReservationRequestDTO;
import web.dto.request.ThemeRequestDTO;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ReservationControllerTest {
    private Long themeId;

    @BeforeEach
    void setUp() {
        ThemeRequestDTO themeRequestDTO = new ThemeRequestDTO("우주테마", "우주선을 몰아서 지구로 가는 테마", 40000);

        ExtractableResponse<Response> themeResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequestDTO)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
        String[] themeLocation = themeResponse.header("Location").split("/");
        themeId = Long.parseLong(themeLocation[themeLocation.length - 1]);
    }

    @DisplayName("예약 생성을 성공하면 201 반환")
    @Test
    void createReservation() {
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO("2023-02-02", "13:00", "name", themeId);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationRequestDTO)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("유효하지 않은 날짜로 예약 생성하면 400 반환")
    @Test
    void invalidDateTimeReservation() {
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO("2023-13-11", "13:00", "name", themeId);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationRequestDTO)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("시간대가 겹치는 예약을 하게되면 409 반환")
    @Test
    void duplicateDateTimeReservation() {
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO("2023-02-02", "13:00", "name", themeId);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationRequestDTO)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationRequestDTO)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @DisplayName("예약 조회를 성공하면 200 반환")
    @Test
    void retrieveReservation() {
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO("2023-02-02", "13:00", "name", themeId);

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationRequestDTO)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        long reservationId = response.jsonPath().getLong("id");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/" + reservationId)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE);
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
    void deleteReservation() {
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO("2023-02-02", "13:00", "name", themeId);

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationRequestDTO)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        long reservationId = response.jsonPath().getLong("id");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations/" + reservationId)
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
