package roomescape.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationRequestDTO;
import roomescape.exception.DuplicatedReservationException;
import roomescape.exception.NoSuchReservationException;
import roomescape.repository.ReservationRepository;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    private final ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO("2022-08-11", "13:00",
            "name");

    private final Reservation mockedReservation = Reservation.builder().build();

    @DisplayName("겹치는 시간대의 예약이 존재할 경우 예외가 발생한다.")
    @Test
    void saveWhenDuplicate() {
        when(reservationRepository.findByDateAndTime(any(LocalDate.class), any(LocalTime.class))).thenReturn(
                Optional.of(mockedReservation));

        assertThatThrownBy(() -> reservationService.save(reservationRequestDTO)).isInstanceOf(
                DuplicatedReservationException.class);
    }

    @DisplayName("해당 예약을 찾지 못할 경우 예외가 발생한다.")
    @Test
    void findById() {
        when(reservationRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reservationService.findById(1L)).isInstanceOf(NoSuchReservationException.class);
    }

    @DisplayName("존재하지 않는 예약을 취소하는 경우 예외가 발생한다.")
    @Test
    void deleteById() {
        when(reservationRepository.deleteById(any(Long.class))).thenReturn(false);

        assertThatThrownBy(() -> reservationService.deleteById(1L)).isInstanceOf(NoSuchReservationException.class);
    }
}
