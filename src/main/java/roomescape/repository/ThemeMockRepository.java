package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.model.Theme;
import java.util.Optional;

@Repository
public class ThemeMockRepository implements ThemeRepository {
    @Override
    public long save(Theme theme) {
        return 0;
    }

    @Override
    public Optional<Theme> findOneByName(String themeName) {
        return Optional.of(new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000));
    }

    @Override
    public void deleteByName(String themeName) {

    }
}
