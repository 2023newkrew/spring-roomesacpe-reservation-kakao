package nextstep.controller;

import io.restassured.RestAssured;
import nextstep.dto.ReservationRequestDto;
import nextstep.repository.ReservationDao;
import nextstep.service.ReservationService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.hamcrest.core.Is.is;


@DisplayName("Reservation Test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    ReservationService reservationService;
    @Autowired
    ReservationDao reservationDao;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        ReservationRequestDto requestDto = new ReservationRequestDto(
                "2023-01-10",
                "13:00:00",
                "jay"
        );
        reservationService.reserve(requestDto);
    }

    @AfterEach
    void afterEach() {
        reservationDao.clear();
    }

    @DisplayName("Reservation - 예약하기")
    @Test
    void reserve() {
        ReservationRequestDto requestDto = new ReservationRequestDto(
                "2023-01-11",
                "13:00:00",
                "jay"
        );

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/reservations/2");
    }

    @DisplayName("Reservation - 예약하기(예외)")
    @Test
    void reserveException() {
        ReservationRequestDto requestDto = new ReservationRequestDto(
                "2023-01-10",
                "13:00:00",
                "jay"
        );

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(is("이미 예약된 날짜와 시간입니다."));
    }

    @DisplayName("Reservation - 조회하기")
    @Test
    void show() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", is(1))
                .body("date", is("2023-01-10"))
                .body("time", is("13:00:00"))
                .body("name", is("jay"))
                .body("themeName", is("워너고홈"))
                .body("themeDesc", is("병맛 어드벤처 회사 코믹물"))
                .body("themePrice", is(29000));
    }

    @DisplayName("Reservation - 취소하기")
    @Test
    void delete() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}