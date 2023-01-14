package nextstep.roomescape.reservation;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.roomescape.reservation.model.Reservation;
import nextstep.roomescape.reservation.model.Theme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.core.Is.is;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {

    @LocalServerPort
    int port;
    private Theme theme;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29000);
    }


    @DisplayName("예약 생성")
    @Test
    void createReservation() {
        Reservation reservation = createRequest(LocalDate.parse("2099-01-01"));

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
        String id = response.response().getHeader("Location").split("/")[2];
        deleteReservation(Long.parseLong(id));

    }

    @DisplayName("예약 생성 예외처리")
    @Test
    void createReservationDuplicate() {
        Reservation reservation = createRequest(LocalDate.parse("2099-02-02"));
        ExtractableResponse<Response> response = createReservation(reservation);
        String id = response.response().getHeader("Location").split("/")[2];
        try {
            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(reservation)
                    .when().post("/reservations")
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(is("예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있습니다."));
        } finally {
            deleteReservation(Long.parseLong(id));
        }


    }


    @DisplayName("예약 검색")
    @Test
    void showReservation() {

        ExtractableResponse<Response> reservation = createReservation(createRequest(LocalDate.parse("2099-03-03")));
        String id = reservation.response().getHeader("Location").split("/")[2];
        try {
            RestAssured.given().log().all()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .when().get("/reservations/" + id)
                    .then().log().all()
                    .statusCode(HttpStatus.OK.value())
                    .body("date", is("2099-03-03"))
                    .body("time", is("13:00:00"));
        } finally {
            deleteReservation(Long.parseLong(id));
        }

    }


    @DisplayName("예약 삭제")
    @Test
    void deleteReservation() {
        ExtractableResponse<Response> reservation = createReservation(createRequest(LocalDate.parse("2099-04-04")));
        String id = reservation.response().getHeader("Location").split("/")[2];
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations/" + id)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    private Reservation createRequest(LocalDate date) {
        final LocalTime time = LocalTime.parse("13:00");
        final String name = "kakao";
        return new Reservation(null, date, time, name, theme);
    }

    private ExtractableResponse<Response> createReservation(Reservation reservation) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> deleteReservation(Long id) {
        return RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations/" + id)
                .then().log().all()
                .extract();
    }
}