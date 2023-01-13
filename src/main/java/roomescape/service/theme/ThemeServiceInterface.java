package roomescape.service.theme;

import java.util.List;
import roomescape.dto.Theme;

public interface ThemeServiceInterface {

    Long create(Theme theme);

    List<Theme> list();
}
