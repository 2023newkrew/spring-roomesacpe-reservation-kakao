package nextstep.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.common.DatabaseExecutor;
import nextstep.dto.request.CreateReservationRequest;
import nextstep.dto.request.CreateOrUpdateThemeRequest;
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
import static nextstep.common.fixture.ThemeProvider.테마_생성을_요청한다;
import static nextstep.common.fixture.ReservationProvider.예약_생성을_요청한다;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationAcceptanceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseExecutor databaseExecutor;

    private String themeName = "혜회 잡화점";

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        databaseExecutor.clearAll();
        테마_생성을_요청한다(new CreateOrUpdateThemeRequest(themeName, "테마 설명", 23_000));
    }

    @Test
    void 예약_생성에_성공한다() {
        // given
        CreateReservationRequest createReservationRequest = new CreateReservationRequest("2023-01-09", "13:00", "eddie-davi", themeName);

        // when
        ExtractableResponse<Response> response = 예약_생성을_요청한다(createReservationRequest);
        
        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void 날짜와_시간이_같은_예약은_생성할_수_없다() {
        // given
        CreateReservationRequest createReservationRequest = new CreateReservationRequest("2023-01-09", "13:00", "eddie-davi", themeName);
        예약_생성을_요청한다(createReservationRequest);

        // when
        ExtractableResponse<Response> response = 예약_생성을_요청한다(createReservationRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 예약_아이디로_예약을_조회한다() {
        // given
        CreateReservationRequest createReservationRequest = new CreateReservationRequest("2023-01-09", "13:00", "davi-eddie", themeName);
        ExtractableResponse<Response> response = 예약_생성을_요청한다(createReservationRequest);

        given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)

        // when
        .when()
                .get(response.header("Location"))

        // then
        .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(1))
                .body("date", equalTo(createReservationRequest.getDate()))
                .body("time", equalTo(createReservationRequest.getTime()))
                .body("name", equalTo(createReservationRequest.getName()));
    }

    @Test
    void 존재하지_않는_예약을_조회할_수_없다() {
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
    void 예약_아이디에_해당하는_예약을_삭제한다() {
        // given
        CreateReservationRequest createReservationRequest = new CreateReservationRequest("2023-01-09", "13:00", "eddie-davi", themeName);
        ExtractableResponse<Response> response = 예약_생성을_요청한다(createReservationRequest);

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
                    .formParam("themeName", "베니스 상인의 저택")

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
                    .formParam("themeName", "베니스 상인의 저택")

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
                    .formParam("themeName", "베니스 상인의 저택")

            // when
            .when()
                    .post("/reservations")

            // then
            .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void 예약_시_방탈출_테마가_기재되지_않으면_예약을_생성할_수_없다() {
            // given
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .formParam("date", "2023-01-09")
                    .formParam("time", "13:00")
                    .formParam("name", "eddie-davi")

            // when
            .when()
                    .post("/reservations")

            // then
            .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

    }

}
