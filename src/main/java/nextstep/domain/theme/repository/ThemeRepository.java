package nextstep.domain.theme.repository;

import nextstep.domain.theme.Theme;

public interface ThemeRepository {

    static Theme getDefaultTheme() {
        return new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);
    }

}
