package roomescape.controller;

import io.restassured.RestAssured;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;
import roomescape.reservation.dto.ReservationRequestDto;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.core.Is.is;

@DisplayName("Http Method")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservationTest {
    private final static LocalDate testDate = LocalDate.of(2023, 1, 1);
    private final static LocalTime testTime = LocalTime.of(13, 0);
    private final static String testName = "hi";
    private final static Long testThemeId = 3L;
    private ReservationRequestDto reservationRequestDto = ReservationRequestDto.builder()
            .date(testDate)
            .time(testTime)
            .name(testName)
            .themeId(testThemeId)
            .build();
    private final RestTemplate restTemplate = new RestTemplate();
    private String baseUrl;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DROP TABLE RESERVATION IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE RESERVATION(" +
                "    id          bigint not null auto_increment,\n" +
                "    date        date,\n" +
                "    time        time,\n" +
                "    name        varchar(20),\n" +
                "    theme_id bigint not null,\n" +
                "    primary key (id)\n)");
        RestAssured.port = port;
        baseUrl = "http://localhost:" + port;
    }

    @DisplayName("Http Method - POST")
    @Test
    @Order(1)
    void createReservation() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationRequestDto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/reservations/1");
    }

    @DisplayName("Http Method - POST Exception")
    @Test
    void createReservationDuplicateException() {
        restTemplate.postForEntity(baseUrl + "/reservations", reservationRequestDto, JSONObject.class);
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationRequestDto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CONFLICT.value())
                .body(is("이미 예약된 시간입니다."));
    }

    @DisplayName("Http Method - GET")
    @Test
    void showReservation() {
        URI path = restTemplate.postForEntity(baseUrl + "/reservations", reservationRequestDto, JSONObject.class)
                .getHeaders().getLocation();
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(path)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("name", is("hi"))
                .body("date", is("2023-01-01"))
                .body("time", is("13:00"));
    }

    @DisplayName("Http Method - GET Exception")
    @Test
    void showReservationException() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/0")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(is("존재하지 않는 예약입니다."));
    }

    @DisplayName("Http Method - DELETE")
    @Test
    void deleteReservation() {
        URI path = restTemplate.postForEntity(baseUrl + "/reservations", reservationRequestDto, JSONObject.class)
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
                .body(is("존재하지 않는 예약입니다."));
    }
}
