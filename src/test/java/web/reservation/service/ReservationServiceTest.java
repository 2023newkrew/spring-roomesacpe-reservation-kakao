package web.reservation.service;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import web.exception.ErrorCode;
import web.reservation.dto.ReservationRequestDto;
import web.reservation.repository.ReservationRepository;
import web.theme.exception.ThemeException;
import web.theme.repository.ThemeRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ThemeRepository themeRepository;

    @Nested
    class ReservationTest {

        @Test
        void should_throwException_when_notExistTheme() {
            LocalDate today = LocalDate.now();
            LocalTime now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
            String name = "name";
            long notExistThemeId = -1L;
            final var requestDto = ReservationRequestDto.builder()
                    .date(today)
                    .time(now)
                    .name(name)
                    .themeId(notExistThemeId)
                    .build();

            when(themeRepository.findById(notExistThemeId)).thenReturn(Optional.empty());
            assertThatThrownBy(() -> reservationService.reservation(requestDto))
                    .isInstanceOf(ThemeException.class)
                    .hasMessage(ErrorCode.THEME_NOT_FOUND.getMessage());
        }
    }
}