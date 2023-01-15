package roomescape.theme.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.theme.dto.request.ThemeRequestDTO;
import roomescape.theme.entity.Theme;
import roomescape.theme.exception.AlreadyReservedThemeException;
import roomescape.theme.exception.DuplicatedThemeException;
import roomescape.theme.exception.NoSuchThemeException;
import roomescape.theme.repository.ThemeRepository;

@ExtendWith(MockitoExtension.class)
class ThemeServiceTest {

    @InjectMocks
    private ThemeService themeService;

    @Mock
    private ThemeRepository themeRepository;

    @Mock
    private ReservationRepository reservationRepository;

    private final Theme mockedTheme = Theme.builder().build();

    private final Reservation mockedReservation = Reservation.builder().build();

    private final ThemeRequestDTO themeRequestDTO = ThemeRequestDTO.builder()
            .name("name")
            .desc("desc")
            .price(1000)
            .build();

    @DisplayName("테마 이름이 중복될 경우 예외가 발생한다.")
    @Test
    void saveWhenDuplicate() {
        when(themeRepository.findByName(any(String.class))).thenReturn(Optional.of(mockedTheme));

        assertThatThrownBy(() -> this.themeService.save(this.themeRequestDTO)).isInstanceOf(
                DuplicatedThemeException.class);
    }

    @DisplayName("지우려는 테마가 없을 경우 예외가 발생한다.")
    @Test
    void deleteById() {
        when(reservationRepository.findByThemeId(any(Long.class))).thenReturn(Optional.empty());
        when(themeRepository.deleteById(any(Long.class))).thenReturn(false);

        assertThatThrownBy(() -> this.themeService.deleteById(1L)).isInstanceOf(
                NoSuchThemeException.class);
    }

    @DisplayName("테마가 이미 예약되어 있을 경우 변경(삭제)할 수 없다.")
    @Test
    void deleteAlreadyReserved() {
        when(reservationRepository.findByThemeId(any(Long.class))).thenReturn(Optional.of(mockedReservation));

        assertThatThrownBy(() -> this.themeService.deleteById(1L)).isInstanceOf(
                AlreadyReservedThemeException.class);
    }
}
