package web.theme.service;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import web.entity.Reservation;
import web.exception.ErrorCode;
import web.reservation.repository.ReservationRepository;
import web.theme.exception.ThemeException;
import web.theme.repository.ThemeRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ThemeServiceTest {

    @InjectMocks
    private ThemeService themeService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ThemeRepository themeRepository;

    @Nested
    class Delete {

        @Test
        void should_successfully_when_reservationNotExist() {
            long themeId = 1L;

            when(reservationRepository.findAllByThemeId(themeId)).thenReturn(List.of());
            when(themeRepository.delete(themeId)).thenReturn(1L);

            assertThatNoException().isThrownBy(() -> themeService.deleteById(themeId));
        }

        @Test
        void should_throwException_when_reservationExist() {
            LocalDate today = LocalDate.now();
            LocalTime now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
            String name = "name";
            long themeId = 1L;
            final var reservation = Reservation.of(1L, today, now, name, themeId);

            when(reservationRepository.findAllByThemeId(themeId)).thenReturn(List.of(reservation));

            assertThatThrownBy(() -> themeService.deleteById(themeId))
                    .isInstanceOf(ThemeException.class)
                    .hasMessage(ErrorCode.RESERVATION_EXIST.getMessage());
        }
    }
}