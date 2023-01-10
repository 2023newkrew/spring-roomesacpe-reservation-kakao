package nextstep.web;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.model.Reservation;
import nextstep.model.Theme;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoomEscapeControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @AfterEach
    void tearDown() {
        reservationRepository.deleteAll();
    }

    @DisplayName("예약을 생성한다")
    @Test
    void createReservation() {
        ExtractableResponse<Response> response = 예약_생성("name", LocalDate.of(2022, 11, 11), LocalTime.of(13, 0));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();

        Reservation reservation = reservationRepository.findById(1L).orElseThrow();
        assertThat(reservation.getDate()).isEqualTo(LocalDate.of(2022, 11, 11));
        assertThat(reservation.getTime()).isEqualTo(LocalTime.of(13, 0));
        assertThat(reservation.getName()).isEqualTo("name");
        assertThat(reservation.getTheme()).isEqualTo(new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000));
    }

    @DisplayName("예약을 조회한다")
    @Test
    void getReservation() {
        Long id = 1L;
        예약_생성("name", LocalDate.of(2022, 11, 11), LocalTime.of(13, 0));

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().log().all()
                .get("/reservations/" + id)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        ReservationResponse reservation = response.as(ReservationResponse.class);
        assertThat(reservation.getDate()).isEqualTo(LocalDate.of(2022, 11, 11));
        assertThat(reservation.getTime()).isEqualTo(LocalTime.of(13, 0));
        assertThat(reservation.getName()).isEqualTo("name");
        assertThat(reservation.getId()).isEqualTo(1L);
        assertThat(reservation.getThemeName()).isEqualTo("워너고홈");
        assertThat(reservation.getThemeDesc()).isEqualTo("병맛 어드벤처 회사 코믹물");
        assertThat(reservation.getThemePrice()).isEqualTo(29_000);
    }

    @DisplayName("에약을 삭제한다")
    @Test
    void deleteReservation() {
        Long id = 1L;
        예약_생성("name", LocalDate.of(2022, 11, 11), LocalTime.of(13, 0));

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().log().all()
                .delete("/reservations/" + id)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(reservationRepository.findById(id)).isEmpty();
    }

    private static ExtractableResponse<Response> 예약_생성(String name, LocalDate date, LocalTime time) {
        ReservationRequest request = new ReservationRequest(name, date, time);

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().log().all()
                .post("/reservations")
                .then().log().all()
                .extract();
    }
}
