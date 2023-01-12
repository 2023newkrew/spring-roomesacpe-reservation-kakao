package nextstep.reservation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.reservation.dto.ReservationRequestDto;
import nextstep.reservation.exceptions.exception.DuplicateReservationException;
import nextstep.reservation.repository.ReservationMemoryRepository;

import org.junit.jupiter.api.Test;

class ReservationServiceTest {

    private final ReservationService service;
    private final ReservationRequestDto reservationRequestDto = ReservationRequestDto.builder()
            .date(LocalDate.of(1982, 2, 19))
            .time(LocalTime.of(2, 2))
            .name("name")
            .build();

    public ReservationServiceTest() {
        service = new ReservationService(new ReservationMemoryRepository());
    }

    @Test
    void 원하는_시간에_예약이_되어있는지_확인할_수_있다() {
        service.addReservation(reservationRequestDto);
        assertThat(service.isDuplicatedReservation(reservationRequestDto)).isTrue();
    }
    @Test
    void 예약이_차있는_시간에_예약을_하게_되면_예외가_발생한다() {
        service.addReservation(reservationRequestDto);
        assertThatThrownBy(() -> service.addReservation(reservationRequestDto))
                .isInstanceOf(DuplicateReservationException.class);
    }

}