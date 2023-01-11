package nextstep.controller;

import io.restassured.RestAssured;
import nextstep.domain.dto.CreateReservationDTO;
import nextstep.repository.WebAppReservationRepository;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.core.Is.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.given().log().all()
                .when().delete("/reservations/all");
    }

    @DisplayName("create reservation test")
    @Test
    void createReservation() {


        CreateReservationDTO reservationDto = new CreateReservationDTO("2022-08-11", "13:35", "name");
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationDto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/reservations/1");
    }

    @DisplayName("duplicate reservation test")
    @Test
    void sameTimeReservationTest() {
        CreateReservationDTO reservationDto = new CreateReservationDTO("2022-08-11", "13:30", "name");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationDto)
                .when().post("/reservations");

        CreateReservationDTO reservationDto2 = new CreateReservationDTO("2022-08-11", "13:30", "name2");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationDto2)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("get reservation test")
    @Test
    void getReservation() {
        CreateReservationDTO reservationDto = new CreateReservationDTO("2022-08-11", "13:30", "name");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationDto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("id", is(1))
                .body("date", is("2022-08-11"))
                .body("time", is("13:30"))
                .body("name", is("name"))
                .body("themeName", is("워너고홈"))
                .body("themeDesc", is("병맛 어드벤처 회사 코믹물"))
                .body("themePrice", is(29_000));
    }

    @DisplayName("delete reservation test")
    @Test
    void deleteReservation() {
        CreateReservationDTO reservationDto = new CreateReservationDTO("2022-08-11", "13:30", "name");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationDto)
                .when().post("/reservations");

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
