package nextstep.web.theme.controller;


import io.restassured.RestAssured;
import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.web.common.repository.RoomEscapeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ThemeControllerTest {

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
    void 테마_생성_조회_삭제() {
        String requestBody = "{\n" +
                "    \"name\": \"테스트테마\",\n" +
                "    \"desc\": \"테스트를 즐겨보아요\",\n" +
                "    \"price\": 10\n" +
                "}";

        String location = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
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
                .when().delete(location)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("message", equalTo(HttpStatus.NO_CONTENT.name()));
    }
}
