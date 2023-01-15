package nextstep.reservation.repository.theme;

import java.util.List;
import java.util.Optional;
import nextstep.reservation.entity.Theme;

public interface ThemeRepository {
    Long add(Theme theme);
    Optional<Theme> findById(Long id);
    List<Theme> findAll();
    boolean delete(Long id);
}
