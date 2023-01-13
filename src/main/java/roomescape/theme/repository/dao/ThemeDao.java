package roomescape.theme.repository.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import roomescape.entity.Theme;

@Repository
public interface ThemeDao {
    Long create(Theme theme);
    Optional<Theme> selectById(Long id);
    List<Theme> selectAll();
    int delete(Long id);
    boolean isThemeNameDuplicated(String themeName);
}
