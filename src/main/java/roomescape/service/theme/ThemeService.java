package roomescape.service.theme;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.reservation.ReservationDAO;
import roomescape.dao.theme.ThemeDAO;
import roomescape.dto.Theme;
import roomescape.exception.BadRequestException;

@Service
public class ThemeService implements ThemeServiceInterface {

    private final ReservationDAO reservationDAO;
    private final ThemeDAO themeDAO;

    public ThemeService(ReservationDAO reservationDAO, ThemeDAO themeDAO) {
        this.reservationDAO = reservationDAO;
        this.themeDAO = themeDAO;
    }

    @Override
    public Long create(Theme theme) {
        validateCreateTheme(theme);
        return themeDAO.create(theme);
    }

    private void validateCreateTheme(Theme theme) {
        throwIfInvalidTheme(theme);
        throwIfExistTheme(theme);
    }

    private void throwIfInvalidTheme(Theme theme) {
        if (theme.getName() == null || theme.getDesc() == null) {
            throw new BadRequestException();
        }
    }

    private void throwIfExistTheme(Theme theme) {
        if (themeDAO.exist(theme)) {
            throw new BadRequestException();
        }
    }

    @Override
    public List<Theme> list() {
        return themeDAO.list();
    }

    @Override
    public void remove(Long id) {
        throwIfNotExistId(id);
        throwIfExistReservationThemeId(id);
        themeDAO.remove(id);
    }

    private void throwIfNotExistId(Long id) {
        if(!themeDAO.existId(id)) {
            throw new BadRequestException();
        }
    }

    private void throwIfExistReservationThemeId(Long id) {
        if(reservationDAO.existThemeId(id)) {
            throw new BadRequestException();
        }
    }
}
