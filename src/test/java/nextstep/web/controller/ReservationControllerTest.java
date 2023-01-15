package nextstep.web.controller;

import io.restassured.RestAssured;
import nextstep.web.dto.ReservationRequestDto;
import nextstep.web.dto.ThemeRequestDto;
import nextstep.web.service.ReservationService;
import nextstep.web.service.ThemeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.Is.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationControllerTest {

    @LocalServerPort
    int port;

    ReservationRequestDto requestDto;

    @Autowired
    ReservationService reservationService;

    @Autowired
    ThemeService themeService;

    @BeforeEach
    @Transactional
    void setUp() {
        RestAssured.port = port;
        ThemeRequestDto themeRequestDto = new ThemeRequestDto(
                "테마이름", "테마설명", 1000
        );
        Long createdThemeId = themeService.create(themeRequestDto);
        requestDto = new ReservationRequestDto(
                LocalDate.of(2023, 1, 10),
                LocalTime.of(13, 0),
                "예약이름",
                createdThemeId
        );
    }

    @Test
    void 예약을_생성할_수_있다() {
        RestAssured.given()
                .log()
                .all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDto)
                .when()
                .post("/reservations")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", startsWith("/reservations"));
    }

    @Test
    void 예약을_조회할_수_있다() {
        Long id = reservationService.createReservation(requestDto);

        RestAssured.given()
                .log()
                .all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/reservations/" + id)
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.OK.value())
                .body("id", is(id.intValue()));
    }

    @Test
    void 예약을_취소할_수_있다() {
        ThemeRequestDto themeRequestDto = new ThemeRequestDto(
                "테마이름", "테마설명", 1000
        );
        Long createdThemeId = themeService.create(themeRequestDto);
        ReservationRequestDto requestDto = new ReservationRequestDto(
                LocalDate.of(2023, 1, 10),
                LocalTime.of(13, 0),
                "예약이름",
                createdThemeId
        );
        Long id = reservationService.createReservation(requestDto);

        RestAssured.given()
                .log()
                .all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/reservations/" + id)
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
