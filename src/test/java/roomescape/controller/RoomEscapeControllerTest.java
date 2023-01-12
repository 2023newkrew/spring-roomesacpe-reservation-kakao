package roomescape.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@DisplayName("Http Method")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class RoomEscapeControllerTest {

    @LocalServerPort
    int port;

    final static Reservation DUMMY_RESERVATION = new Reservation(
            1L,
            LocalDate.of(2022, 8, 11),
            LocalTime.of(13, 0, 0),
            "name22",
            new Theme("Theme", "병맛 어드벤처 회사 코믹물", 30000));

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    /**
     * RoomEscapeController > createReservation 메서드
     */
    @DisplayName("reservation이 잘 생성되는지 확인한다")
    @Test
    void createReservation() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(DUMMY_RESERVATION)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/reservations/1");
    }

    /**
     * RoomEscapeController > createReservation 메서드
     */
    @DisplayName("동일한 날짜/시간대에 예약을 하는 경우, 예외가 발생한다")
    @Test
    void duplicatedReservation() {
        createReservation();
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(DUMMY_RESERVATION)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    /**
     * RoomEscapeController > lookUpReservation 메서드
     */
    @DisplayName("id에 해당하는 reservation 객체를 잘 가져오는지 확인한다")
    @Test
    void lookUpReservation() {
        createReservation();
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", is(1));
    }

    /**
     * RoomEscapeController > deleteReservation 메서드
     */
    @DisplayName("id에 해당하는 reservation 객체를 잘 삭제하는지 확인한다")
    @Test
    void deleteReservation() {
        createReservation();
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
