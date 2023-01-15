package nextstep.web.service;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.web.dto.ReservationRequestDto;
import nextstep.web.repository.ReservationRepository;
import nextstep.web.repository.ThemeRepository;
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
    ReservationRepository reservationRepository;

    @Mock
    ThemeRepository themeRepository;

    @InjectMocks
    ReservationService reservationService;

    Reservation reservation;

    Theme theme;

    ReservationRequestDto requestDto;

    @BeforeEach
    void setUp() {
        theme = new Theme(1L, "워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);
        reservation = Reservation.builder()
                .id(1L)
                .date(LocalDate.of(2023, 1, 10))
                .time(LocalTime.MIDNIGHT)
                .name("베인")
                .theme(theme)
                .build();
        requestDto = new ReservationRequestDto(
                LocalDate.of(2023, 1, 10),
                LocalTime.of(13, 0),
                "예약이름",
                1L
        );
    }

    @Test
    void 예약을_생성할_수_있다() {
        when(reservationRepository.save(any()))
                .thenReturn(1L);
        when(themeRepository.findById(1L))
                .thenReturn(any(Theme.class));

        Assertions.assertThat(reservationService.createReservation(requestDto))
                .isEqualTo(1L);
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
