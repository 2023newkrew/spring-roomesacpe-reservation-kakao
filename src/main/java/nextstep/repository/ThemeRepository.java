package nextstep.repository;

import java.util.Optional;
import nextstep.entity.Theme;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeRepository {
    Theme save(Theme theme);

    Optional<Theme> findById(Long id);

    int update(Theme theme);

    int deleteById(Long id);


}
