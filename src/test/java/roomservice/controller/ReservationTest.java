package roomservice.controller;

import io.restassured.RestAssured;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import roomservice.domain.Reservation;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.core.Is.is;

@DisplayName("Http Method")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservationTest {
    private final static LocalDate testDate = LocalDate.of(2023, 1, 1);
    private Reservation reservation = new Reservation();
    private RestTemplate restTemplate = new RestTemplate();
    private String baseUrl;

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        reservation.setDate(testDate);
        reservation.setName("hi");
        baseUrl = "http://localhost:" + port;
    }

    @DisplayName("Http Method - POST")
    @Test
    @Order(1)
    void createReservation() {
        reservation.setTime(LocalTime.of(1, 0));
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/reservations/1");
    }

    @DisplayName("Http Method - POST Exception")
    @Test
    void createReservationDuplicateException() {
        reservation.setTime(LocalTime.of(2, 0));
        restTemplate.postForEntity(baseUrl + "/reservations", reservation, JSONObject.class);
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(is("이미 예약된 시간입니다."));
    }

    @DisplayName("Http Method - GET")
    @Test
    void showReservation() {
        reservation.setTime(LocalTime.of(3, 0));
        URI path = restTemplate.postForEntity(baseUrl + "/reservations", reservation, JSONObject.class)
                .getHeaders().getLocation();
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(path)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("name", is("hi"));
    }

    @DisplayName("Http Method - GET Exception")
    @Test
    void showReservationException() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/0")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(is("존재하지 않는 예약 id입니다."));
    }


    @DisplayName("Http Method - DELETE")
    @Test
    void deleteReservation() {
        reservation.setTime(LocalTime.of(4, 0));
        URI path = restTemplate.postForEntity(baseUrl + "/reservations", reservation, JSONObject.class)
                .getHeaders().getLocation();
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(path)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("Http Method - DELETE Exception")
    @Test
    void deleteReservationException() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations/0")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(is("존재하지 않는 예약 id입니다."));
    }
}
