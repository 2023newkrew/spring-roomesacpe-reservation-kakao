package nextstep.repository.integration;

import io.restassured.RestAssured;
import nextstep.dto.web.request.CreateReservationRequest;
import nextstep.util.DatabaseCleaner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static nextstep.domain.Theme.DEFAULT_THEME;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringStartsWith.startsWith;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ReservationIntegrationTest {

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        databaseCleaner.clear();
        databaseCleaner.insertInitialData();
    }

    private CreateReservationRequest createReservationRequest = CreateReservationRequest.of(
            "2022-08-11",
            "13:00",
            "jin",
            1L
    );

    private void createReservation(CreateReservationRequest request) {
        RestAssured
            .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
            .when()
                .post("/reservations");
    }

    @Test
    void should_haveCreatedLocationAtHeader_when_createRequestSent() {
        RestAssured
            .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(CreateReservationRequest.of("2022-08-11", "13:00", "jin", 1L))
            .when()
                .post("/reservations")
            .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", startsWith("/reservations/"));
    }

    @Test
    void should_response400BadRequestStatus_when_createDuplicateDateTimeRequestSent() {
        //given
        createReservation(createReservationRequest);

        RestAssured
            .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createReservationRequest)
            .when()
                .post("/reservations")
            .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void should_returnReservation_when_findRequestWithIdSent() {
        //given
        createReservation(createReservationRequest);

        RestAssured
            .when()
                .get("/reservations/1")
            .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("date", is(createReservationRequest.getDate()))
                .body("time", is(createReservationRequest.getTime()))
                .body("name", is(createReservationRequest.getName()))
                .body("themeName", is(DEFAULT_THEME.getName()))
                .body("themeDesc", is(DEFAULT_THEME.getDesc()))
                .body("themePrice", is(DEFAULT_THEME.getPrice()));

    }

    @Test
    void should_response400BadRequestStatus_when_findRequestWithInvalidIdSent() {
        RestAssured
            .when()
                .get("/reservations/1")
            .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void should_deleteReservation_when_deleteRequestSent() {
        //given
        createReservation(createReservationRequest);

        RestAssured
            .when()
                .delete("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
