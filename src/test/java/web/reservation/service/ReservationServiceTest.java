package web.reservation.service;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import web.entity.Theme;
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
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
        void should_successfully_when_existTheme() {
            final var today = LocalDate.now();
            final var now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
            final var name = "name";
            final var existThemeId = 1L;
            final var reservationId = 1L;
            final var requestDto = ReservationRequestDto.builder()
                    .date(today)
                    .time(now)
                    .name(name)
                    .themeId(existThemeId)
                    .build();
            final var theme = Theme.builder()
                    .id(existThemeId)
                    .name("name")
                    .desc("desc")
                    .price(22000)
                    .build();

            when(themeRepository.findById(existThemeId)).thenReturn(Optional.of(theme));
            when(reservationRepository.save(any())).thenReturn(reservationId);

            final var result = reservationService.reservation(requestDto);

            assertThat(result).isEqualTo(reservationId);
        }

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