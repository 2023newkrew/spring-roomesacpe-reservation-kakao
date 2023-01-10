package nextstep.web;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.model.Reservation;
import nextstep.model.Theme;
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

    @DisplayName("예약을 생성한다")
    @Test
    void createReservation() {
        ReservationRequest request = new ReservationRequest("name", LocalDate.of(2022, 11, 11), LocalTime.of(13, 00));

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().log().all()
                .post("/reservations")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();

        Reservation reservation = reservationRepository.findById(1L).orElseThrow();
        assertThat(reservation.getDate()).isEqualTo(LocalDate.of(2022, 11, 11));
        assertThat(reservation.getTime()).isEqualTo(LocalTime.of(13, 0));
        assertThat(reservation.getName()).isEqualTo("name");
        assertThat(reservation.getTheme()).isEqualTo(new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000));
    }

}
