package nextstep.repository;

import nextstep.domain.theme.Theme;

public interface ThemeRepo {
    public Theme findById(long id);

    public long save(Theme theme);

    public int delete(long id);
}
