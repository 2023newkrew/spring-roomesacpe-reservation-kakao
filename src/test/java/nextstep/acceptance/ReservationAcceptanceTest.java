package nextstep.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.repository.ReservationRepository;
import nextstep.dto.CreateReservationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        reservationRepository.deleteAll();
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
    void 날짜와_시간이_같은_예약은_생성할_수_없다() {
        // given
        CreateReservationRequest createReservationRequest = new CreateReservationRequest("2023-01-09", "13:00", "eddie-davi");
        createReservation(createReservationRequest);

        // when
        ExtractableResponse<Response> response = createReservation(createReservationRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void id로_예약을_조회한다() {
        // given
        CreateReservationRequest createReservationRequest = new CreateReservationRequest("2023-01-09", "13:00", "davi-eddie");
        ExtractableResponse<Response> response = createReservation(createReservationRequest);
        String expected = response.header("Location").split("/")[2];

        given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)

        // when
        .when()
                .get(response.header("Location"))

        // then
        .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(Integer.valueOf(expected)))
                .body("date", equalTo(createReservationRequest.getDate()))
                .body("time", equalTo(createReservationRequest.getTime()))
                .body("name", equalTo(createReservationRequest.getName()));
    }

    @Test
    void 존재하지_않는_예약을_조회하면_예외가_발생한다() {
        // given
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)

        // when
        .when()
                .get("/reservations/1")

        // then
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void id에_해당하는_예약을_삭제한다() {
        // given
        CreateReservationRequest createReservationRequest = new CreateReservationRequest("2023-01-09", "13:00", "eddie-davi");
        ExtractableResponse<Response> response = createReservation(createReservationRequest);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)

                // when
        .when()
                .delete(response.header("Location"))

                // then
        .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Nested
    @DisplayName("예약 생성 예외 테스트")
    class InvalidCreateReservationTest {

        @Test
        void 예약_시_날짜가_기재되지_않으면_예약을_생성할_수_없다() {
            // given
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .formParam("time", "13:00")
                    .formParam("name", "eddie-davi")

            // when
            .when()
                    .post("/reservations")

            // then
            .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void 예약_시_시간이_기재되지_않으면_예약을_생성할_수_없다() {
            // given
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .formParam("date", "2023-01-09")
                    .formParam("name", "eddie-davi")

                    // when
                    .when()
                    .post("/reservations")

                    // then
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void 예약_시_이름이_기재되지_않으면_예약을_생성할_수_없다() {
            // given
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .formParam("date", "2023-01-09")
                    .formParam("time", "13:00")

                    // when
                    .when()
                    .post("/reservations")

                    // then
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

    }

    private ExtractableResponse<Response> createReservation(CreateReservationRequest createReservationRequest) {
        return given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createReservationRequest)
        .when()
                .post("/reservations")
        .then()
                .extract();
    }

}
