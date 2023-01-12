package reservation;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import reservation.model.domain.Reservation;
import reservation.model.domain.Theme;
import reservation.model.dto.RequestReservation;
import reservation.respository.ReservationJdbcTemplateRepository;
import reservation.respository.ThemeJdbcTemplateRepository;

import java.time.LocalDate;
import java.time.LocalTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTest {
    @LocalServerPort
    int port;

    private final ThemeJdbcTemplateRepository themeRepository;
    private final ReservationJdbcTemplateRepository reservationRepository;

    @Autowired
    public ControllerTest(ThemeJdbcTemplateRepository themeRepository, ReservationJdbcTemplateRepository reservationRepository) {
        this.themeRepository = themeRepository;
        this.reservationRepository = reservationRepository;
        LocalDate date = LocalDate.of(2023, 1, 2);
        LocalTime time = LocalTime.of(11, 0);
        themeRepository.save(new Theme(1L, "name", "desc", 10000));
        reservationRepository.save(new Reservation(1L, date, time, "TEST", 1L));
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("POST /reservations")
    @Test
    void postReservation() {
        LocalDate localDate = LocalDate.of(2023, 1, 1);
        LocalTime localTime = LocalTime.of(11, 0);

        RequestReservation requestReservation = new RequestReservation(localDate, localTime, "TEST", 1L);

        given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestReservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/reservations/3");
    }

    @DisplayName("GET /reservations/2")
    @Test
    void getReservation() {
        given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/2")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", is(2))
                .body("name", is("TEST"));
    }

    @DisplayName("DELETE /reservations/1 → GET /reservations/1")
    @Test
    void deleteReservation() {
        given().log().all()
                .accept(MediaType.TEXT_PLAIN_VALUE)
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .body(is(""));

        given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
