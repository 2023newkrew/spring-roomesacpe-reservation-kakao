package roomescape.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestExecutionListeners;
import roomescape.domain.Reservation;
import roomescape.repository.Reservation.JdbcReservationRepository;
import roomescape.service.Reservation.WebReservationService;

import java.time.LocalDate;
import java.time.LocalTime;

@DisplayName("Http Method")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(value = {AcceptanceTestExecutionListener.class,}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public class ConsoleReservationControllerTest {

    @LocalServerPort
    int port;

    Reservation reservation;
    ConsoleReservationController consoleReservationController;

    public ConsoleReservationControllerTest() throws ClassNotFoundException {
        ConsoleReservationController consoleReservationController = new ConsoleReservationController(
                new WebReservationService(new JdbcReservationRepository())
        );
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        reservation = new Reservation(13131L,
                LocalDate.of(2013,1,12),
                LocalTime.of(14,0,0),
                "name23",
                12L);
    }

    @DisplayName("방탈출 예약이 가능함")
    @Test
//    @Transactional
    void createReservationTest() {

    }

    @DisplayName("방탈출 예약이 되었다면 조회할 수 있음")
    @Test
    void showReservationTest() {

    }

    @DisplayName("방탈출 예약이 되었다면 취소할 수 있음")
    @Test
    void deleteReservationTest() {

    }

    @DisplayName("동일한 날짜/시간대에 예약을 하는 경우, 예외가 발생")
    @Test
    void duplicatedReservationTest(){

    }
}

