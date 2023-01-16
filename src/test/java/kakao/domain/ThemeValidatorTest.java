package kakao.domain;

import domain.Reservation;
import domain.Theme;
import domain.ThemeValidator;
import kakao.error.exception.DuplicatedThemeException;
import kakao.error.exception.UsingThemeException;
import kakao.repository.ReservationMemRepository;
import kakao.repository.ThemeMemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ThemeValidatorTest {
    @DisplayName("같은 이름을 가진 theme가 있으면 DuplicateTheme 예외를 발생한다")
    @Test
    void validateForSameName() {
        ThemeMemRepository themeRepository = new ThemeMemRepository();
        ReservationMemRepository reservationRepository = new ReservationMemRepository();
        ThemeValidator validator = new ThemeValidator(themeRepository, reservationRepository);

        themeRepository.save(new Theme("themeName", "themeDesc", 1000));

        Assertions.assertThatExceptionOfType(DuplicatedThemeException.class)
                .isThrownBy(() -> validator.validateForSameName("themeName"));
    }

    @Test
    void validateForUsingTheme() {
        ThemeMemRepository themeRepository = new ThemeMemRepository();
        ReservationMemRepository reservationRepository = new ReservationMemRepository();
        reservationRepository.save(Reservation.builder()
                .theme(new Theme(1L, "themeName", "themeDesc", 1000))
                .build());

        ThemeValidator validator = new ThemeValidator(themeRepository, reservationRepository);

        Assertions.assertThatExceptionOfType(UsingThemeException.class)
                .isThrownBy(() -> validator.validateForUsingTheme(1L));
    }
}
