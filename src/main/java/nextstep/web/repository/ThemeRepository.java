package nextstep.web.repository;

import java.util.List;
import nextstep.domain.Theme;

public interface ThemeRepository {
    Long insertWithKeyHolder(Theme theme);
    Theme findById(Long id);
    Theme findByName(String name);
    List<Theme> getAllThemes();
    Integer delete(Long id);
}
