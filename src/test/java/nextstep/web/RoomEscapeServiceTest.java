package nextstep.web;

import nextstep.exception.ReservationDuplicateException;
import nextstep.exception.ReservationNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;


class RoomEscapeServiceTest {

    @DisplayName("존재하지 않는 예약을 조회할 경우 예외가 발생한다")
    @Test
    void getNotFoundReservation() {
        ReservationRepository reservationRepository = new ReservationRepository();
        RoomEscapeService service = new RoomEscapeService(reservationRepository);

        assertThatThrownBy(() -> service.getReservation(1L))
                .isInstanceOf(ReservationNotFoundException.class);
    }

    @DisplayName("예약 생성시 같은 날짜와 시간의 예약이 존재할 경우 예외가 발생한다")
    @Test
    void createDuplicateReservation() {
        ReservationRepository reservationRepository = new ReservationRepository();
        RoomEscapeService service = new RoomEscapeService(reservationRepository);

        ReservationRequest request = new ReservationRequest("name", LocalDate.of(2022, 11, 11), LocalTime.of(10, 10));
        service.createReservation(request);

        assertThatThrownBy(() -> service.createReservation(request))
                .isInstanceOf(ReservationDuplicateException.class);
    }
}
