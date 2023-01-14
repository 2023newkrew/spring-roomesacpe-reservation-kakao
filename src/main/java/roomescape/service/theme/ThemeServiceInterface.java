package roomescape.service.theme;

import java.util.List;
import org.springframework.lang.NonNull;
import roomescape.dto.Theme;

public interface ThemeServiceInterface {

    Long create(@NonNull Theme theme);
    List<Theme> list();
    void remove(long id);
    Theme find(long id);
}
