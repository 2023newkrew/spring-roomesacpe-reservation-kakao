package nextstep.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.dto.CreateReservationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

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

        // when
        ExtractableResponse<Response> response = createReservation(createReservationRequest);
        
        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }


    @Test
    void id로_예약을_조회한다() {
        // given
        CreateReservationRequest createReservationRequest = new CreateReservationRequest("2023-01-09", "13:00", "davi-eddie");
        ExtractableResponse<Response> response = createReservation(createReservationRequest);
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)

        // when
        .when().get(response.header("Location"))

        // then
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(1))
                .body("date", equalTo(createReservationRequest.getDate()))
                .body("time", equalTo(createReservationRequest.getTime()))
                .body("name", equalTo(createReservationRequest.getName()));
    }

    @Test
    void 존재하지_않는_예약을_조회하면_예외가_발생한다() {
        
    }

    private ExtractableResponse<Response> createReservation(CreateReservationRequest createReservationRequest) {
        return given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createReservationRequest)
        .when()
                .post("/reservations")
        .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
    }

}
