package nextstep.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import nextstep.dto.ReservationRequest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.core.Is.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void createReservation() {
        ReservationRequest reservation = new ReservationRequest(LocalDate.parse("2022-08-11"),LocalTime.parse("22:24"),"jordy");
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation).when().post("/reservations").
                then().log().all().statusCode(HttpStatus.SC_CREATED)
                .header("Location", "/reservations/1");
    }

    @Test
    void getReservation() {
        ReservationRequest reservation = new ReservationRequest(LocalDate.parse("2022-08-11"),LocalTime.parse("22:24"),"jordy");
        RestAssured.given().contentType(ContentType.JSON).body(reservation).when().post("/reservations");
        RestAssured.given()
                .log().all().
                accept(ContentType.JSON).when().get("/reservations/1").then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("id", is(1))
                .body("date", is("2022-08-11"))
                .body("time", is("22:24:00"))
                .body("name", is("jordy"))
                .body("theme.name", is("워너고홈"))
                .body("theme.desc", is("병맛"))
                .body("theme.price", is(29000));
    }

    @Test
    void deleteReservation() {
        ReservationRequest reservation = new ReservationRequest(LocalDate.parse("2022-08-11"),LocalTime.parse("22:24"),"jordy");
        RestAssured.given().contentType(ContentType.JSON).body(reservation).when().post("/reservations");
        RestAssured.given()
                .log().all()
                .when().delete("/reservations/1").then()
                .log().all()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    void duplicateReservation() {
        ReservationRequest reservation = new ReservationRequest(LocalDate.parse("2022-08-11"),LocalTime.parse("22:24"),"jordy");
        RestAssured.given().contentType(ContentType.JSON).body(reservation).when().post("/reservations");
        RestAssured.given()
                .log().all()
                .contentType(ContentType.JSON).body(reservation).when().post("/reservations").then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(is("IllegalArgumentException"));
    }
}