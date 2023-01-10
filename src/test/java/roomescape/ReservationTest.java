package roomescape;

import io.restassured.RestAssured;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import roomescape.domain.ReservationRequest;

@DisplayName("Reservation Test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("예약 생성 테스트")
    @Test
    void createReservation() {
        ReservationRequest reservationRequest = new ReservationRequest("2022-08-11", "13:00", "name");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservationRequest)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/reservations/1");
    }

    @Test
    @DisplayName("예약 거절 테스트")
    void rejectReservation() throws Exception {
        // Given
        ReservationRequest reservationRequest = new ReservationRequest("2022-08-11", "13:00", "kayla");
        ReservationRequest overlapReservationRequest = new ReservationRequest("2022-08-11", "13:00", "jerrie");

        // When
        ResultActions resultActions = this.mockMvc.perform(post("/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(reservationRequest))
        );
        ResultActions overlapResultActions = this.mockMvc.perform(post("/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(overlapReservationRequest))
        );

        // Then
        overlapResultActions.andExpect(status().isUnprocessableEntity());
    }


    @DisplayName("예약 조회 테스트")
    @Test
    void showReservation() throws Exception {
        String name = "kayla";
        ReservationRequest reservationRequest = new ReservationRequest("2022-08-11", "13:00", name);

        ResultActions resultActions = this.mockMvc.perform(post("/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(reservationRequest))
        );

        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", is(1))
                .body("name", is(name));
    }
}
