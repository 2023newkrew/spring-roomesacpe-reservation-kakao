package reservation;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import reservation.model.dto.RequestReservation;

import java.time.LocalDate;
import java.time.LocalTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTest {
    @LocalServerPort
    int port;

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
                .header("Location", "/reservations/1");
    }

    @DisplayName("GET /reservations/1")
    @Test
    void getReservation() {
        given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", is(1))
                .body("username", is("TEST"));
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
