package kakao.controller;

import io.restassured.RestAssured;
import kakao.Initiator;
import kakao.dto.request.CreateReservationRequest;
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

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.core.Is.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationControllerTest {

    @LocalServerPort
    int port;

    private final Initiator initiator;

    private final String path = "reservations";
    private final CreateReservationRequest request = new CreateReservationRequest(
            LocalDate.of(2023, 10, 13),
            LocalTime.of(13, 00),
            "baker",
            1L
    );

    @Autowired
    ReservationControllerTest(JdbcTemplate jdbcTemplate) {
        initiator = new Initiator(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        initiator.createThemeForTest();
    }

    @AfterEach
    void clear() {
        initiator.clear();
    }

    @DisplayName("예약 생성이 성공하면 201 status를 반환한다")
    @Test
    void createReservation() {
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post(path)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/reservations/1");
    }

    @DisplayName("중복된 예약이 발생하면 400 status를 반환한다")
    @Test
    void createDuplicate() {
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .post(path);

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("reservations")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("reservations/{id}로 reservation을 조회하면 200 status와 해당되는 reservation의 response를 응답으로 반환한다")
    @Test
    void getReservation() {
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .post(path);

        RestAssured.given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get(path + "/1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", is(request.name))
                .body("date", is("2023-10-13"))
                .body("time", is("13:00"))
                .body("id", is(1));
    }

    @DisplayName("reservations/{id}로 존재하지 않는 reservation ID를 조회하면 400 status를 반환한다")
    @Test
    void getNoReservation() {
        RestAssured.given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get(path + "/100")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
