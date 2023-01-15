package roomescape.controller;

import com.sun.jdi.request.DuplicateRequestException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import roomescape.domain.Reservation;
import roomescape.repository.Reservation.JdbcReservationRepository;
import roomescape.service.Reservation.WebReservationService;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Console Test")
@SpringBootTest
@TestExecutionListeners(value = {AcceptanceTestExecutionListener.class,}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public class ConsoleReservationControllerTest {
    Reservation reservation;
    ConsoleReservationController consoleReservationController;

    @Autowired
    public ConsoleReservationControllerTest() throws ClassNotFoundException {
        this.consoleReservationController = new ConsoleReservationController(
                new WebReservationService(new JdbcReservationRepository())
        );
    }

    @BeforeEach
    void setUp() {
        reservation = new Reservation(0L,
                LocalDate.of(2013,1,12),
                LocalTime.of(14,0,0),
                "name23",
                12L);
    }

    @DisplayName("방탈출 예약이 가능하고 조회할 수 있음")
    @Test
//    @Transactional
    void createReservationTest() {
        Reservation createReservation = consoleReservationController.createReservation(reservation);
        Reservation findReservation = consoleReservationController.lookUpReservation(createReservation.getId());
        Assertions.assertThat(createReservation.getDate()).isEqualTo(findReservation.getDate());
        Assertions.assertThat(createReservation.getTime()).isEqualTo(findReservation.getTime());
        Assertions.assertThat(createReservation.getName()).isEqualTo(findReservation.getName());
        Assertions.assertThat(createReservation.getThemeId()).isEqualTo(findReservation.getThemeId());
    }

    @DisplayName("방탈출 예약이 되었다면 취소할 수 있음")
    @Test
    void deleteReservationTest() {
        Reservation createReservation = consoleReservationController.createReservation(reservation);
        consoleReservationController.deleteReservation(createReservation.getId());
//        Reservation reservation1 = consoleReservationController.lookUpReservation(reservation.getId());
    }

    @DisplayName("동일한 날짜/시간대에 예약을 하는 경우, 예외가 발생")
    @Test
    void duplicatedReservationTest(){
        consoleReservationController.createReservation(reservation);
        assertThrows(DuplicateRequestException.class, () ->
                consoleReservationController.createReservation(reservation)
        );
    }

}

