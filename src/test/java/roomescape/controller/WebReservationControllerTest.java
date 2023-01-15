package roomescape.controller;

import com.sun.jdi.request.DuplicateRequestException;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.test.context.TestExecutionListeners;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@DisplayName("Reservation Test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(value = {AcceptanceTestExecutionListener.class,}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public class WebReservationControllerTest {

    @LocalServerPort
    int port;

    Reservation reservation;
    @Autowired
    WebReservationController roomEscapeController;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        reservation = new Reservation(0L,
                LocalDate.of(2013,1,12),
                LocalTime.of(14,0,0),
                "name23",
                12L);
    }

    @DisplayName("방탈출 예약이 가능함")
    @Test
//    @Transactional
    void createReservationTest() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("방탈출 예약이 되었다면 조회할 수 있음")
    @Test
    void showReservationTest() {
        String createBody = roomEscapeController.createReservation(reservation).getBody();
        String reserveId = Objects.requireNonNull(createBody).split("/")[2];
        RestAssured.given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/" + reserveId)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("방탈출 예약이 되었다면 취소할 수 있음")
    @Test
    void deleteReservationTest() {
        String createBody = roomEscapeController.createReservation(reservation).getBody();
        String deleteId = Objects.requireNonNull(createBody).split("/")[2];
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations/" + deleteId)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("동일한 날짜/시간대에 예약을 하는 경우, 예외가 발생")
    @Test
    void duplicatedReservationTest(){
        roomEscapeController.createReservation(reservation);
        Assertions.assertThrows(DuplicateRequestException.class, () ->
            roomEscapeController.createReservation(reservation)
        );
    }
}

