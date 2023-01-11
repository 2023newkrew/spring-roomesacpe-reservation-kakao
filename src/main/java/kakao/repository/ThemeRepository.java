package kakao.repository;

import java.util.List;
import kakao.domain.Theme;

public interface ThemeRepository {

    Theme save(Theme reservation);

    List<Theme> findAll();

    Theme findById(Long id);

    int delete(Long id);
}
