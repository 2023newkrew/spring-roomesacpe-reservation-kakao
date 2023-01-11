package nextstep.common.fixture;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.dto.request.CreateReservationRequest;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;

public class ReservationProvider {

    public static ExtractableResponse<Response> 예약_생성을_요청한다(CreateReservationRequest createReservationRequest) {
        return given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createReservationRequest)
        .when()
                .post("/reservations")
        .then()
                .extract();
    }

}
