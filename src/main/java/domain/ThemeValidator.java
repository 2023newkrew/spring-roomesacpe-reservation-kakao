package domain;

import kakao.error.exception.DuplicatedThemeException;
import kakao.error.exception.UsingThemeException;

import java.util.List;

public class ThemeValidator {
    public void validateForSameName(List<Theme> themes) {
        if (!themes.isEmpty()) throw new DuplicatedThemeException();
    }

    public void validateForUsingTheme(List<Reservation> reservationsUsingTheme) {
        if (!reservationsUsingTheme.isEmpty()) throw new UsingThemeException();
    }
}
