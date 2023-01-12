package nextstep.domain.theme.repository;

import nextstep.domain.theme.Theme;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository {

    static Theme getDefaultTheme() {
        return new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);
    }

    Theme save(Theme theme);
    Optional<Theme> findByName(String name);
    List<Theme> findAll(int page, int offset);
    void deleteById(Long id);

}
