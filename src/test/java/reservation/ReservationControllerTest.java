package reservation;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import reservation.domain.dto.request.ReservationRequest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.core.Is.is;

@Sql({"schema.sql", "data.sql"})
@DisplayName("Reservation Controller Test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {
    @LocalServerPort
    int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @AfterEach
    void setDown() {
        String[] sqls = {"DELETE FROM reservation", "DELETE FROM theme"};
        for (String sql : sqls) {
            jdbcTemplate.update(sql);
        }
    }

    @DisplayName("POST /reservations")
    @Test
    void createReservation() {
        LocalDate localDate = LocalDate.of(2023, 1, 1);
        LocalTime localTime = LocalTime.of(11, 0);

        ReservationRequest reservationRequest = new ReservationRequest(localDate, localTime, "RYO", 1);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationRequest)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/reservations/2");
    }

    @DisplayName("GET /reservations/1")
    @Test
    void getReservation() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", is(1))
                .body("name", is("TEST"));
    }

    @DisplayName("DELETE /reservations/1 → GET /reservations/1")
    @Test
    void deleteReservation() {
        RestAssured.given().log().all()
                .accept(MediaType.TEXT_PLAIN_VALUE)
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .body(is(""));

        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
