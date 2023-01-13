package nextstep.web.theme.controller;


import io.restassured.RestAssured;
import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.web.common.exception.CommonErrorCode;
import nextstep.web.common.repository.RoomEscapeRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@TestMethodOrder(MethodOrderer.MethodName.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ThemeControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    RoomEscapeRepository<Reservation> reservationDao;

    Long reservationId;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        reservationId = reservationDao.save(Reservation.of(
                LocalDate.of(2022, 2, 11),
                LocalTime.of(13, 0),
                "tester",
                1L
        ));
    }

    @AfterEach
    void afterEach() {
        reservationDao.deleteById(reservationId);
    }

    @Test
    void 테마_생성_조회_수정_조회_삭제() {
        String createBody = "{\n" +
                "    \"name\": \"테스트테마\",\n" +
                "    \"desc\": \"테스트를 즐겨보아요\",\n" +
                "    \"price\": 10\n" +
                "}";

        String updateBody = "{\n" +
                "    \"name\": \"바꿔바꿔\",\n" +
                "    \"desc\": \"바꿔바꿔바꿔바꿔\",\n" +
                "    \"price\": 1000\n" +
                "}";

        String location = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createBody)
                .when().post(Theme.BASE_URL)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("data.location", containsString(Theme.BASE_URL))
                .extract().body().jsonPath().get("data.location");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(location)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("data.name", equalTo("테스트테마"));

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(updateBody)
                .when().put(location)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo(HttpStatus.OK.name()));

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(location)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("data.name", equalTo("바꿔바꿔"));

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(location)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo(HttpStatus.NO_CONTENT.name()));
    }

    @Test
    void 예약_있는_테마_삭제시도시_405_반환() {
        final String themeLocation = "/themes/1";

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(themeLocation)
                .then().log().all()
                .statusCode(CommonErrorCode.RESERVED_THEME_ERROR.getHttpStatus().value())
                .body("message", equalTo(CommonErrorCode.RESERVED_THEME_ERROR.getMessage()));
    }

    @Test
    void 예약_있는_테마_수정시도시_405_반환() {
        String requestBody = "{\n" +
                "    \"name\": \"바꾸기\",\n" +
                "    \"desc\": \"테스트를 즐겨보아요\",\n" +
                "    \"price\": 10\n" +
                "}";

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().put("/themes/1")
                .then().log().all()
                .statusCode(CommonErrorCode.RESERVED_THEME_ERROR.getHttpStatus().value())
                .body("message", equalTo(CommonErrorCode.RESERVED_THEME_ERROR.getMessage()));
    }
}
