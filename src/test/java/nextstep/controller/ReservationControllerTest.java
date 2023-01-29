package nextstep.controller;

import io.restassured.RestAssured;
import nextstep.domain.dto.ReservationRequest;
import nextstep.domain.dto.ThemeRequest;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.core.Is.is;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        ThemeRequest dto = new ThemeRequest(
                "test name",
                "test desc",
                10000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(dto)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/themes/1");
    }

    @DisplayName("can POST reservation")
    @Test
    void can_POST_reservation() {
        ReservationRequest dto = new ReservationRequest(
                "2000-01-01",
                "00:00",
                "name",
                1L);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(dto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/reservations/1");
    }

    @DisplayName("can reject POST of duplicate date and time")
    @Test
    void can_reject_POST_of_duplicate_date_and_time() {
        this.can_POST_reservation();

        ReservationRequest duplicatedDto = new ReservationRequest(
                "2000-01-01",
                "00:00",
                "different name",
                1L);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(duplicatedDto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("can GET reservation of given id")
    @Test
    void can_GET_reservation_of_given_id() {
        this.can_POST_reservation();

        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("id", is(1))
                .body("date", is("2000-01-01"))
                .body("time", is("00:00"))
                .body("name", is("name"))
                .body("themeName", is("test name"))
                .body("themeDesc", is("test desc"))
                .body("themePrice", is(10000));
    }

    @DisplayName("can reject GET of nonexistent id")
    @Test
    void can_reject_GET_of_nonexistent_id() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("can DELETE reservation of given id")
    @Test
    void can_DELETE_reservation_of_given_id() {
        this.can_POST_reservation();

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("can reject DELETE of nonexistent id")
    @Test
    void can_reject_DELETE_of_nonexistent_id() {
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
