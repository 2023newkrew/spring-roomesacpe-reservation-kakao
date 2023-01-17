package nextstep.domains.reservation.controller;

import io.restassured.RestAssured;
import nextstep.domain.reservation.dto.ReservationRequestDto;
import nextstep.domain.reservation.repository.ReservationJdbcTemplateRepository;
import nextstep.domain.reservation.service.ReservationService;
import nextstep.domain.theme.dto.ThemeRequestDto;
import nextstep.domain.theme.service.ThemeService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.core.Is.is;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Reservation Test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    ReservationService reservationService;
    @Autowired
    ReservationJdbcTemplateRepository reservationJdbcTemplateRepository;
    @Autowired
    ThemeService themeService;

    @BeforeAll
    void setTheme() {
        ThemeRequestDto themeRequestDto = new ThemeRequestDto(
                "워너고홈",
                "병맛 어드벤처 회사 코믹물",
                29_000
        );
        themeService.add(themeRequestDto);
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto(
                1L, LocalDate.parse("2023-01-10"),
                LocalTime.parse("13:00"),
                "jay"
        );
        reservationService.reserve(reservationRequestDto);
    }

    @AfterEach
    void afterEach() {
        reservationJdbcTemplateRepository.clear();
    }

    @DisplayName("Reservation - 예약하기")
    @Test
    void reserve() {
        ReservationRequestDto requestDto = new ReservationRequestDto(
                1L, LocalDate.parse("2023-01-11"),
                LocalTime.parse("13:00:00"),
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
                1L, LocalDate.parse("2023-01-10"),
                LocalTime.parse("13:00:00"),
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