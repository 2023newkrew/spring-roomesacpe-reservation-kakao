package nextstep.acceptance;

import io.restassured.RestAssured;
import nextstep.dto.CreateReservationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationAcceptanceTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 예약_생성_요청에_성공한다() {
        // given
        CreateReservationRequest createReservationRequest = new CreateReservationRequest("2023-01-09", "13:00", "eddie-davi");

        given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(createReservationRequest)

        // when
        .when()
                .post("/reservations")

        // then
        .then()
                .statusCode(HttpStatus.CREATED.value());
    }

}
