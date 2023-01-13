package nextstep.web.reservation.controller;

import io.restassured.RestAssured;
import nextstep.domain.Reservation;
import nextstep.web.common.repository.RoomEscapeRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;


import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    RoomEscapeRepository<Reservation> reservationDao;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @Transactional
    void 예약_정상_생성_조회_삭제() {
        String requestBody = "{\n" +
                "    \"date\": \"2022-02-11\",\n" +
                "    \"time\": \"13:00\",\n" +
                "    \"name\": \"tester\",\n" +
                "    \"themeId\": 1\n" +
                "}";

        String location = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post(Reservation.BASE_URL)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("data.location", containsString(Reservation.BASE_URL))
                .extract().body().jsonPath().get("data.location");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(location)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("data.name", equalTo("tester"));

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(location)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }
}
