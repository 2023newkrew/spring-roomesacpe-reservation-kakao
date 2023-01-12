package nextstep.web.service;

import nextstep.domain.Reservation;
import nextstep.web.dto.CreateReservationRequestDto;
import nextstep.web.repository.RoomEscapeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    RoomEscapeRepository<Reservation> reservationRepository;

    @InjectMocks
    ReservationService reservationService;

    Reservation reservation;

    @BeforeEach
    void setUp() {
        reservation = Reservation.builder()
                .id(1L)
                .date(LocalDate.of(2023, 1, 10))
                .time(LocalTime.MIDNIGHT)
                .name("베인")
                .themeId(1L)
                .build();
    }

    @Test
    void 예약을_생성할_수_있다() {
        CreateReservationRequestDto requestDto = new CreateReservationRequestDto(
                LocalDate.of(2023, 1, 10), LocalTime.of(13, 0), "reservation1", 1L
        );
        when(reservationRepository.save(any()))
                .thenReturn(1L);

        Assertions.assertThat(reservationService.createReservation(requestDto).getLocation())
                .isEqualTo("/reservations/1");
    }

    @Test
    void 예약을_조회할_수_있다() {
        Long id = 1L;

        when(reservationRepository.findById(anyLong()))
                .thenReturn(reservation);

        Assertions.assertThat(reservationService.findReservation(id)
                        .getId())
                .isEqualTo(id);
        verify(reservationRepository, times(1)).findById(id);
    }

    @Test
    void 예약을_취소할_수_있다() {
        Long id = 1L;
        doNothing().when(reservationRepository)
                .deleteById(anyLong());

        Assertions.assertThatNoException()
                .isThrownBy(() -> reservationService.deleteReservation(id));
        verify(reservationRepository, times(1)).deleteById(id);
    }
}
