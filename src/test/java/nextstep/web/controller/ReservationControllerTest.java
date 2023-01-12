package nextstep.web.controller;

import io.restassured.RestAssured;
import nextstep.domain.Reservation;
import nextstep.web.common.repository.RoomEscapeRepository;
import nextstep.web.reservation.dto.CreateReservationRequestDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
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
        CreateReservationRequestDto requestDto = new CreateReservationRequestDto(
                LocalDate.of(2023, 1, 10), LocalTime.of(13, 0), "tester", 1L
        );

        String location = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDto)
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
