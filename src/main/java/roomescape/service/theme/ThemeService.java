package roomescape.service.theme;

import java.util.List;
import roomescape.dao.theme.ThemeDAO;
import roomescape.dto.Theme;
import roomescape.exception.BadRequestException;

public class ThemeService implements ThemeServiceInterface {

    private final ThemeDAO themeDAO;

    public ThemeService(ThemeDAO themeDAO) {
        this.themeDAO = themeDAO;
    }

    @Override
    public Long create(Theme theme) {
        throwIfExistTheme(theme);
        return themeDAO.create(theme);
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
}
