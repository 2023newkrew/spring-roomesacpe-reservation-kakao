package nextstep.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.domain.Reservation;
import nextstep.exceptions.ErrorCode;
import nextstep.exceptions.ReservationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("Reservation Service")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationServiceTest {
    @Autowired
    private ReservationService reservationService;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        reservation = new Reservation(
                LocalDate.of(2022, 8, 11),
                LocalTime.of(13, 0),
                "name"
        );
    }

    @Test
    @DisplayName("동일한 날짜, 시간에 예약이 존재할 경우 예외 처리 테스트")
    public void reservationAlreadyExistsTest() {
        reservationService.createReservation(reservation);
        assertThatThrownBy(() -> reservationService.createReservation(reservation))
                .isInstanceOf(ReservationException.class)
                .hasMessage(ErrorCode.ALREADY_RESERVATION_EXISTS.getMessage());
    }

    @Test
    @DisplayName("조회한 예약이 없을 경우 예외 처리 테스트")
    public void noViewedReservationTest() {
        assertThatThrownBy(() -> reservationService.lookupReservation(100L))
                .isInstanceOf(ReservationException.class)
                .hasMessage(ErrorCode.RESERVATION_NOT_FOUND.getMessage());
    }
}
