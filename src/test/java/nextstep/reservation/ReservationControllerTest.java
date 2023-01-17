package nextstep.reservation;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import nextstep.reservation.dto.ThemeRequest;
import nextstep.reservation.dto.ThemeResponse;
import nextstep.reservation.entity.Reservation;
import nextstep.reservation.service.ReservationService;
import nextstep.reservation.service.ThemeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalTime;

import static nextstep.reservation.exception.RoomEscapeExceptionCode.DUPLICATE_TIME_RESERVATION;
import static org.hamcrest.core.Is.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {

    @LocalServerPort
    int port;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ThemeService themeService;

    @AfterEach
    void tearDown() {
        reservationService.clear();
        themeService.clear();
    }

    @DisplayName("예약 등록시 201이 반환되며 /reservations/{id}로 이동한다.")
    @Test
    void registerReservation() {
        //given
        RestAssured.port = port;

        ThemeRequest themeRequest = new ThemeRequest("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
        ThemeResponse createdTheme = themeService.registerTheme(themeRequest);

        Reservation reservation = new Reservation(null, LocalDate.parse("2022-08-12"), LocalTime.parse("13:00"), "name", createdTheme.getId());

        //when
        //then
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("이미 예약이 존재하는 시간에 예약 등록 시도할 때 예외 발생한다.")
    @Test
    void registerReservationDuplicate() {
        //given
        RestAssured.port = port;
        ThemeRequest themeRequest = new ThemeRequest("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
        ThemeResponse createdTheme = themeService.registerTheme(themeRequest);

        Reservation reservation = new Reservation(null, LocalDate.parse("2022-08-12"), LocalTime.parse("13:00"), "name", createdTheme.getId());
        registerReservation(reservation);

        Reservation reservationDuplicated = new Reservation(null, reservation.getDate(), reservation.getTime(), "name2", reservation.getThemeId());
        //when
        //then
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationDuplicated)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(is(DUPLICATE_TIME_RESERVATION.getMessage()));
    }

    @DisplayName("예약 조회시 body에 예약 정보가 정해진 형식으로 주어진다.")
    @Test
    void showReservation() {
        //given
        RestAssured.port = port;

        ThemeRequest themeRequest = new ThemeRequest("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
        ThemeResponse createdTheme = themeService.registerTheme(themeRequest);

        Reservation reservation = new Reservation(null, LocalDate.parse("2022-08-12"), LocalTime.parse("13:00"), "name", createdTheme.getId());
        Response createResponse = registerReservation(reservation);

        String id = createResponse.getHeader("Location").split("/")[2]; // Redirect 주소: /reservations/id

        //when
        //then
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/" + id)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("date", is(reservation.getDate().toString()))
                .body("time", is(reservation.getTime().toString()));
    }

    @Test
    @DisplayName("id를 통한 예약 삭제시 204를 반환한다.")
    void deleteReservation() {
        //given
        RestAssured.port = port;

        ThemeRequest themeRequest = new ThemeRequest("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
        ThemeResponse createdTheme = themeService.registerTheme(themeRequest);

        Reservation reservation = new Reservation(null, LocalDate.parse("2022-08-12"), LocalTime.parse("13:00"), "name", createdTheme.getId());
        Response response = registerReservation(reservation);

        String id = response.getHeader("Location").split("/")[2]; // Redirect 주소: /reservations/id

        //when
        //then
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations/" + id)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("예약 전체 삭제시 204를 반환한다.")
    void clearAll() {
        //given
        RestAssured.port = port;

        //when
        //then
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    private Response registerReservation(Reservation reservation) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .extract()
                .response();
    }
}