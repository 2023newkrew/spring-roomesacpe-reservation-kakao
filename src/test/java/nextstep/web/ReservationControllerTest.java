package nextstep.web;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.model.Reservation;
import nextstep.model.Theme;
import nextstep.dto.ReservationRequest;
import nextstep.dto.ReservationResponse;
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
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcTemplateReservationRepository repository;

    @Autowired
    private JdbcTemplateThemeRepository themeRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("DELETE FROM reservation");
        jdbcTemplate.execute("DELETE FROM theme");
    }

    @DisplayName("예약을 생성한다")
    @Test
    void createReservation() {
        Theme theme = themeRepository.save(new Theme(null, "새로운 테마", "테마테마", 23100));

        String name = "예약_이름";
        LocalDate date = LocalDate.of(2022, 12, 14);
        LocalTime time = LocalTime.of(15, 5);
        ReservationRequest request = new ReservationRequest(name, date, time, theme.getId());

        ExtractableResponse<Response> response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/reservations")
                .then()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();

        Long id = 생성된_예약_번호를_반환한다(response);
        Reservation reservation = repository.findById(id).orElseThrow();
        assertThat(reservation.getDate()).isEqualTo(date);
        assertThat(reservation.getTime()).isEqualTo(time);
        assertThat(reservation.getName()).isEqualTo(name);
        assertThat(reservation.getThemeId()).isEqualTo(theme.getId());
    }

    @DisplayName("중복된 예약 생성은 예외를 응답한다")
    @Test
    void createDuplicateReservation() {
        Theme theme = themeRepository.save(new Theme(null, "새로운 테마", "테마테마", 23100));

        String name = "예약_이름";
        LocalDate date = LocalDate.of(2022, 12, 14);
        LocalTime time = LocalTime.of(15, 5);
        예약_생성_후_번호를_반환한다(name, date, time, theme.getId());

        ReservationRequest request = new ReservationRequest(name, date, time, theme.getId());

        ExtractableResponse<Response> response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/reservations")
                .then()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(response.body()).isNotNull();
    }

    @DisplayName("예약을 조회한다")
    @Test
    void getReservation() {
        Theme theme = themeRepository.save(new Theme(null, "새로운 테마", "테마테마", 23100));

        String name = "예약_이름";
        LocalDate date = LocalDate.of(2022, 4, 3);
        LocalTime time = LocalTime.of(12, 15);
        Long id = 예약_생성_후_번호를_반환한다(name, date, time, theme.getId());

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/reservations/" + id)
                .then()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        ReservationResponse reservation = response.as(ReservationResponse.class);
        assertThat(reservation.getDate()).isEqualTo(date);
        assertThat(reservation.getTime()).isEqualTo(time);
        assertThat(reservation.getName()).isEqualTo(name);
        assertThat(reservation.getThemeName()).isEqualTo(theme.getName());
        assertThat(reservation.getThemeDesc()).isEqualTo(theme.getDesc());
        assertThat(reservation.getThemePrice()).isEqualTo(theme.getPrice());
    }

    @DisplayName("없는 예약을 조회하면 예외를 응답한다")
    @Test
    void getNotFoundReservation() {
        Long id = 1L;

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/reservations/" + id)
                .then()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.body()).isNotNull();
    }

    @DisplayName("에약을 삭제한다")
    @Test
    void deleteReservation() {
        Theme theme = themeRepository.save(new Theme(null, "새로운 테마", "테마테마", 23100));

        String name = "취소될 예약";
        LocalDate date = LocalDate.of(2023, 5, 29);
        LocalTime time = LocalTime.of(8, 30);
        Long id = 예약_생성_후_번호를_반환한다(name, date, time, theme.getId());

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/reservations/" + id)
                .then()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(repository.findById(id)).isEmpty();
    }

    private Long 생성된_예약_번호를_반환한다(ExtractableResponse<Response> response) {
        String id = response
                .header(HttpHeaders.LOCATION)
                .split("/")[2];
        return Long.parseLong(id);
    }

    private Long 예약_생성_후_번호를_반환한다(String name, LocalDate date, LocalTime time, Long themeId) {
        ReservationRequest request = new ReservationRequest(name, date, time, themeId);

        String id =  RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/reservations")
                .then()
                .extract()
                .header(HttpHeaders.LOCATION)
                .split("/")[2];
        return Long.parseLong(id);
    }
}
