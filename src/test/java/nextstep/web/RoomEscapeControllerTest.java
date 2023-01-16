package nextstep.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import io.restassured.RestAssured;
import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.model.Reservation;
import nextstep.model.Theme;
import nextstep.repository.ReservationRepository;
import nextstep.web.dto.ReservationRequest;
import nextstep.web.dto.ReservationResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoomEscapeControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ReservationRepository repository;

    Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @DisplayName("예약을 생성한다")
    @Test
    void createReservation() {
        String name = "예약_이름";
        LocalDate date = LocalDate.of(2022, 12, 14);
        LocalTime time = LocalTime.of(15, 5);

        Long id = 예약을_생성한다(name, date, time);

        Reservation reservation = repository.findById(id).orElseThrow();
        assertThat(reservation.getDate()).isEqualTo(date);
        assertThat(reservation.getTime()).isEqualTo(time);
        assertThat(reservation.getName()).isEqualTo(name);
        assertThat(reservation.getTheme()).isEqualTo(theme);
    }

    @DisplayName("예약을 조회한다")
    @Test
    void getReservation() {
        String name = "예약_이름";
        LocalDate date = LocalDate.of(2022, 4, 3);
        LocalTime time = LocalTime.of(12, 15);
        Long id = 예약을_생성한다(name, date, time);

        ReservationResponse reservation = 예약을_조회한다(id);

        assertThat(reservation.getDate()).isEqualTo(date);
        assertThat(reservation.getTime()).isEqualTo(time);
        assertThat(reservation.getName()).isEqualTo(name);
        assertThat(reservation.getThemeName()).isEqualTo(theme.getName());
        assertThat(reservation.getThemeDesc()).isEqualTo(theme.getDesc());
        assertThat(reservation.getThemePrice()).isEqualTo(theme.getPrice());
    }

    @DisplayName("에약을 삭제한다")
    @Test
    void deleteReservation() {
        Long id = 예약을_생성한다("취소될 예약", LocalDate.of(2023, 5, 29), LocalTime.of(8, 30));

        예약을_삭제한다(id);

        assertThat(repository.findById(id)).isEmpty();
    }

    private Long 예약을_생성한다(String name, LocalDate date, LocalTime time) {
        ReservationRequest request = new ReservationRequest(name, date, time);

        String id = RestAssured.given().log().all().contentType(MediaType.APPLICATION_JSON_VALUE).body(request).when()
                .post("/reservations").then().statusCode(HttpStatus.CREATED.value())
                .header(HttpHeaders.LOCATION, notNullValue()).extract().header(HttpHeaders.LOCATION).split("/")[2];

        return Long.parseLong(id);
    }

    private ReservationResponse 예약을_조회한다(Long id) {
        return RestAssured.given().log().all().contentType(MediaType.APPLICATION_JSON_VALUE).when()
                .get("/reservations/" + id).then().statusCode(HttpStatus.OK.value()).extract()
                .as(ReservationResponse.class);
    }

    private void 예약을_삭제한다(Long id) {
        RestAssured.given().log().all().contentType(MediaType.APPLICATION_JSON_VALUE).when()
                .delete("/reservations/" + id).then().statusCode(HttpStatus.NO_CONTENT.value()).extract();
    }
}
