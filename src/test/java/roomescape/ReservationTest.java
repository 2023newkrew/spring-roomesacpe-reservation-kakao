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

    @BeforeAll
    void setUp() throws Exception {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("예약 생성 거절 테스트")
    void rejectCreateReservation() throws Exception {
        // Given
        ReservationRequest overlapReservationRequest = new ReservationRequest("2022-08-11", "13:00", "jerrie");

        // When
        ResultActions overlapResultActions = mockMvc.perform(post("/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(overlapReservationRequest))
        );

        // Then
        overlapResultActions.andExpect(status().isUnprocessableEntity());
    }


    @DisplayName("예약 조회 테스트")
    @Test
    void showReservation() throws Exception {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", is(1))
                .body("name", is(name));
    }

    @DisplayName("예약 조회 거절 테스트")
    @Test
    void rejectShowReservation() throws Exception {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/10")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("예약 삭제 테스트")
    @Test
    void deleteReservation() throws Exception {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations/2")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("예약 삭제 거절 테스트")
    @Test
    void rejectDeleteReservation() throws Exception {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations/20")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

}
