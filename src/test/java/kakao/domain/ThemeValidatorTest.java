package kakao.domain;

import domain.Reservation;
import domain.Theme;
import domain.ThemeValidator;
import kakao.error.exception.DuplicatedThemeException;
import kakao.error.exception.UsingThemeException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class ThemeValidatorTest {
    @DisplayName("같은 이름을 가진 theme가 있으면 DuplicateTheme 예외를 발생한다")
    @Test
    void validateForSameName() {
        ThemeValidator validator = new ThemeValidator();

        Assertions.assertThatExceptionOfType(DuplicatedThemeException.class)
                .isThrownBy(() -> validator.validateForSameName(List.of(
                        new Theme("name", "desc", 1000)
                )));
    }

    @Test
    void validateForUsingTheme() {
        ThemeValidator validator = new ThemeValidator();

        Assertions.assertThatExceptionOfType(UsingThemeException.class)
                .isThrownBy(() -> validator.validateForUsingTheme(
                        List.of(Reservation.builder()
                                .theme(new Theme(1L, "name", "desc", 1000))
                                .build())
                ));
    }
}
