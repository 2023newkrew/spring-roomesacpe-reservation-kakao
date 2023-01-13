package nextstep.domain.theme.repository;

import nextstep.domain.theme.domain.Theme;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThemeRepository {
    Optional<Theme> findByName(String name);

    Long save(Theme theme);

    void clear();
}