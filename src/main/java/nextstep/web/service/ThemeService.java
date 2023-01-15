package nextstep.web.service;

import java.util.List;
import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.web.exceptions.ErrorCode;
import nextstep.web.exceptions.ThemeException;
import nextstep.web.repository.ReservationRepositoryImpl;
import nextstep.web.repository.ThemeRepositoryImpl;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ThemeService {
    private final ThemeRepositoryImpl themeRepositoryImpl;
    private final ReservationRepositoryImpl reservationRepositoryImpl;

    public ThemeService(ThemeRepositoryImpl themeRepositoryImpl, ReservationRepositoryImpl reservationRepositoryImpl) {
        this.themeRepositoryImpl = themeRepositoryImpl;
        this.reservationRepositoryImpl = reservationRepositoryImpl;
    }

    public Long createTheme(Theme theme) {
        try {
            themeRepositoryImpl.findByName(theme.getName());
        } catch (EmptyResultDataAccessException e) {
            return themeRepositoryImpl.insertWithKeyHolder(theme);
        }
        throw new ThemeException(ErrorCode.ALREADY_THEME_EXISTS);
    }

    public List<Theme> lookupAllThemes() {
        return themeRepositoryImpl.getAllThemes();
    }

    public Theme deleteTheme(Long id) {
        Theme theme;
        try {
            theme = themeRepositoryImpl.findById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ThemeException(ErrorCode.THEME_NOT_FOUND);
        }

        List<Reservation> reservations = reservationRepositoryImpl.findByTheme(theme);
        if (reservations.size() > 0) {
            throw new ThemeException(ErrorCode.RESERVATION_WITH_THIS_THEME_EXISTS);
        }
        themeRepositoryImpl.delete(id);
        return theme;
    }
}
