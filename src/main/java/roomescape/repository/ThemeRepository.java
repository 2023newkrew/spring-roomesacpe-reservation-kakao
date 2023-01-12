package roomescape.repository;

import roomescape.model.Theme;
import java.util.Optional;

public interface ThemeRepository {
    Long save(Theme theme);
    Optional<Theme> find(Long id);
    Integer delete (Long id);
    Boolean has(String name);
}
