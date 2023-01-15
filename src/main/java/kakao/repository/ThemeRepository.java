package kakao.repository;

import kakao.model.Theme;

public interface ThemeRepository {
    Theme DEFAULT_THEME = new Theme("고양이", "야옹야옹", 29_000);
}
