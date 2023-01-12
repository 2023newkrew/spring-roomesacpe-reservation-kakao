package reservation.respository;

import reservation.model.domain.Theme;

import java.util.List;

public interface ThemeRepository {

    Long save(Theme theme);
    List<Theme> findAll();
    int deleteById(Long id);
}
