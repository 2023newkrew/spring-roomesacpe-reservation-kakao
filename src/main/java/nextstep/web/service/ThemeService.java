package nextstep.web.service;

import java.util.List;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.web.exceptions.ErrorCode;
import nextstep.web.exceptions.ThemeException;
import nextstep.web.repository.ReservationDAOImpl;
import nextstep.web.repository.ThemeDAOImpl;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ThemeService {
    private final ThemeDAOImpl themeDAOImpl;
    private final ReservationDAOImpl reservationDAOImpl;

    public ThemeService(ThemeDAOImpl themeDAOImpl, ReservationDAOImpl reservationDAOImpl) {
        this.themeDAOImpl = themeDAOImpl;
        this.reservationDAOImpl = reservationDAOImpl;
    }

    public Long createTheme(Theme theme) {
        List<Theme> themesByName = themeDAOImpl.findByName(theme.getName());
        if (themesByName.size() > 0) {
            throw new ThemeException(ErrorCode.ALREADY_THEME_EXISTS);
        }

        return themeDAOImpl.insertWithKeyHolder(theme);
    }

    public List<Theme> lookupAllThemes() {
        return themeDAOImpl.getAllThemes();
    }

    public Theme deleteTheme(Long id) {
        Theme theme;
        try {
            theme = themeDAOImpl.findById(id);
        } catch(EmptyResultDataAccessException e) {
            throw new ThemeException(ErrorCode.THEME_NOT_FOUND);
        }

        List<Reservation> reservations = reservationDAOImpl.findByTheme(theme);
        if (reservations.size() > 0) {
            throw new ThemeException(ErrorCode.RESERVATION_WITH_THIS_THEME_EXISTS);
        }
        themeDAOImpl.delete(id);
        return theme;
    }
}
